const searchDistribuidor = document.getElementById('searchDistribuidor');
const divHidden = document.getElementById('div-hidden');
const cuadradoSelector = document.getElementById('cuadradoSelector');

searchDistribuidor.addEventListener('focus', function () {
  divHidden.classList.remove('hidden');
});

cuadradoSelector.addEventListener('change', function () {
  const selectedOption = cuadradoSelector.options[cuadradoSelector.selectedIndex];

  searchDistribuidor.value = selectedOption.textContent.trim();

  divHidden.classList.add('hidden');
});

document.addEventListener('click', function (event) {
  if (!searchDistribuidor.contains(event.target) && !divHidden.contains(event.target)) {
    divHidden.classList.add('hidden');
  }
});

// FIN SCRIPTS DE ESTETICA

let loteId = 0;

// Función para cargar productos desde la API y llenar el select
async function cargarProductos() {
    try {
        const response = await fetch("http://localhost:8080/myapp/producto/list");
        if (!response.ok) {
            throw new Error("Error al obtener la lista de productos.");
        }
        const data = await response.json();
        console.log("Productos cargados:", data.data); // Verifica la lista de productos
        return data.data || []; // Devuelve los productos o un array vacío
    } catch (error) {
        console.error("Error al cargar productos:", error);
        return [];
    }
}


async function addLote() {
    loteId++;
    const loteContainer = document.getElementById("loteContainer");

    // Obtener la lista de productos
    const productos = await cargarProductos();

    // Crear opciones para el select
    const opcionesProductos = productos.map(producto => `
        <option value="${producto.id}">${producto.nombre} ${producto.marca}</option>
    `).join("");

    // Crear el HTML del lote con el select de productos y el botón "Agregar Producto"
    const loteHtml = `
        <div id="lote_${loteId}" class="lote">
            <h3>Lote ${loteId}</h3>
            <label>Producto:</label>
            <select name="id_Producto" required>
                <option value="">Seleccione un producto</option>
                ${opcionesProductos}
            </select>
            <!-- Botón para agregar un nuevo producto -->
            <a href="/producto/register" class="btn btn-success" style="margin-left: 10px;">
                <i class="bi bi-plus-circle"></i> Agregar Producto
            </a><br>

            <label>Fecha de Vencimiento:</label>
            <input type="date" name="fechaVencimiento"><br>

            <label>Fecha de Creación:</label>
            <input type="date" name="fechaCreacion"><br>

            <label>Código del Lote:</label>
            <input type="text" name="codigoLote" required minlength="3" maxlength="15"><br>

            <label>Precio de Compra:</label>
            <input type="number" name="precioCompra" step="0.01" required min="0" max="10000"><br>

            <label>Precio de Venta:</label>
            <input type="number" name="precioVenta" step="0.01" required min="0" max="10000"><br>

            <label>Cantidad:</label>
            <input type="number" name="cantidad" required min="0" max="100000"><br>

            <label>Descripción:</label>
            <input type="text" name="descripcionLote" required minlength="3" maxlength="75"><br>

            <button type="button" onclick="removeLote(${loteId})">Eliminar Lote</button><br><br>
        </div>
    `;

    loteContainer.insertAdjacentHTML("beforeend", loteHtml);

    // Agregar los manejadores de evento para los campos de cantidad y precioCompra
    const cantidadInput = document.querySelector(`#lote_${loteId} input[name="cantidad"]`);
    const precioInput = document.querySelector(`#lote_${loteId} input[name="precioCompra"]`);

    cantidadInput.addEventListener("input", calculateTotal);
    precioInput.addEventListener("input", calculateTotal);

    calculateTotal(); // Actualiza el total al agregar un nuevo lote
}


function removeLote(id) {
    const lote = document.getElementById(`lote_${id}`);
    lote.remove();
}

function calculateTotal() {
    const lotes = document.querySelectorAll("#loteContainer > div");
    let totalAmount = 0;

    lotes.forEach((lote) => {
        const cantidadInput = lote.querySelector("input[name='cantidad']");
        const precioInput = lote.querySelector("input[name='precioCompra']"); // Usar precioCompra para calcular el total
        const cantidad = parseFloat(cantidadInput?.value) || 0;
        const precio = parseFloat(precioInput?.value) || 0;
        totalAmount += cantidad * precio;
    });

    // Mostrar el total en el formulario
    const totalElement = document.getElementById("totalAmount");
    if (totalElement) {
        totalElement.textContent = `Monto Total: $${totalAmount.toFixed(2)}`;
    } else {
        // Crear elemento si no existe
        const totalContainer = document.createElement("div");
        totalContainer.id = "totalAmount";
        totalContainer.textContent = `Monto Total: $${totalAmount.toFixed(2)}`;
        document.getElementById("loteContainer").insertAdjacentElement("afterend", totalContainer);
    }

    // Actualizar el campo oculto con el total
    document.getElementById("totalC").value = totalAmount.toFixed(2);

    return totalAmount.toFixed(2); // Devuelve el total calculado
}


document.getElementById("ordenVentaForm").addEventListener("submit", async function (event) {
    event.preventDefault(); // Evitar el envío tradicional del formulario

    // Obtener la fecha de venta
    const fechaVenta = document.getElementById("fechaVenta").value;

    // Obtener los datos de los lotes
    const lotes = [];
    const loteElements = document.querySelectorAll("#loteContainer > div");

    loteElements.forEach((loteElement) => {
        const codigoLote = loteElement.querySelector("input[name='codigoLote']").value;
        const cantidad = parseFloat(loteElement.querySelector("input[name='cantidad']").value);
        const precioVenta = parseFloat(loteElement.querySelector(".precio-text").textContent);

        // Construir el objeto Lote con solo los atributos necesarios
        const lote = {
            codigoLote: codigoLote,
            cantidad: cantidad,
            precioVenta: precioVenta, // Solo enviamos el precio de venta
        };

        lotes.push(lote);
    });

    // Obtener el total de la venta
    const totalVenta = parseFloat(document.getElementById("totalC").value);

    // Construir el objeto OrdenVenta
    const ordenVenta = {
        fechaVenta: fechaVenta,
        loteList: lotes,
        totalVenta: totalVenta,
    };

    try {
        // Enviar la solicitud al servidor
        const response = await fetch("http://localhost:8080/myapp/ordenVenta/save", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(ordenVenta),
        });

        // Verificar si la respuesta es exitosa
        if (response.ok) {
            const result = await response.json();
            mostrarMensaje(result.data, "success");

            // Redirigir a /ventas/list después de 2 segundos
            setTimeout(() => {
                window.location.href = "/ventas/list";
            }, 2000);
        } else {
            // Manejar errores del servidor
            const errorData = await response.json();
            mostrarMensaje(errorData.data || "Error al guardar la orden de venta", "error");
        }
    } catch (error) {
        // Manejar errores de red o excepciones
        mostrarMensaje("Error al enviar la solicitud: " + error.message, "error");
    }
});

function mostrarMensaje(mensaje, tipo) {
    const mensajeElement = document.getElementById("mensaje");
    mensajeElement.textContent = mensaje;
    mensajeElement.className = tipo; // Aplicar la clase de estilo (success o error)
    mensajeElement.style.display = "block"; // Mostrar el mensaje
  
    // Ocultar el mensaje después de 5 segundos
    setTimeout(() => {
      mensajeElement.style.display = "none";
    }, 5000);
  }
