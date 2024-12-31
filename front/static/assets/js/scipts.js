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
        <option value="${producto.id}">${producto.nombre}</option>
    `).join("");

    // Crear el HTML del lote con el select de productos
    const loteHtml = `
        <div id="lote_${loteId}" class="lote">
            <h3>Lote ${loteId}</h3>
            <label>Producto:</label>
            <select name="id_Producto">
                <option value="">Seleccione un producto</option>
                ${opcionesProductos}
            </select><br>
            <label>Fecha de Vencimiento:</label>
            <input type="date" name="fechaVencimiento"><br>
            <label>Fecha de Creación:</label>
            <input type="date" name="fechaCreacion"><br>
            <label>Código del Lote:</label>
            <input type="text" name="codigoLote"><br>
            <label>Precio de Compra:</label>
            <input type="number" name="precioCompra" step="0.01"><br>
            <label>Precio de Venta:</label>
            <input type="number" name="precioVenta" step="0.01"><br>
            <label>Cantidad:</label>
            <input type="number" name="cantidad"><br>
            <label>Descripción:</label>
            <input type="text" name="descripcionLote"><br>
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


document.getElementById("ordenCompraForm").addEventListener("submit", function (event) {
    event.preventDefault(); // Evitar el envío estándar del formulario.

    const formData = new FormData(event.target);
    const data = {
        nro_OrdenCompra: formData.get("nro_Oc"),
        fechaCompra: formData.get("fechaCom"),
        cedula_Distribuidor: formData.get("cedula_dis"),
        loteList: [], // Esto debe ser un arreglo de objetos correctamente serializado
        totalCompra: formData.get("totalC"), // Ahora obtenemos el total desde el campo oculto
    };

    // Procesar lotes
    const lotes = document.querySelectorAll("#loteContainer > div");
    lotes.forEach((lote) => {
        const loteData = {};
        lote.querySelectorAll("input, select").forEach((input) => {
            loteData[input.name] = input.type === "number" ? parseFloat(input.value) : input.value;
        });
        data.loteList.push(loteData);
    });

    // Convertir los datos a JSON
    const jsonData = JSON.stringify(data);

    console.log(jsonData); // Verifica el formato de los datos enviados

    // Enviar los datos como JSON
    fetch("http://localhost:8080/myapp/ordenCompra/save", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: jsonData, // Enviar el JSON como cuerpo de la solicitud
    })
        .then((response) => response.json())
        .then((result) => {
            console.log("Éxito:", result);
        })
        .catch((error) => {
            console.error("Error:", error);
        });
});
