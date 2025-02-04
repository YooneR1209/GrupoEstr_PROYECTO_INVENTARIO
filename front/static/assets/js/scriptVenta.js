let loteId = 0;

async function cargarProductos() {
    try {
        const response = await fetch("http://localhost:8080/myapp/producto/list");
        if (!response.ok) throw new Error("Error al obtener la lista de productos.");
        const data = await response.json();
        return data.data || [];
    } catch (error) {
        console.error("Error al cargar productos:", error);
        return [];
    }
}

async function buscarLotePorCodigo(codigoLote) {
    try {
        console.log(`Buscando lote con código: ${codigoLote}`);
        const response = await fetch(`http://localhost:8080/myapp/lote/list/search/codigoLote/${codigoLote}`);
        if (!response.ok) throw new Error("Error al buscar el lote.");
        const data = await response.json();
        console.log("Respuesta del servidor:", data); // Depuración
        return data.data; // Retorna directamente el objeto del lote
    } catch (error) {
        console.error("Error al buscar lote:", error);
        return null;
    }
}

async function addLote() {
    loteId++;
    const loteContainer = document.getElementById("loteContainer");

    const loteHtml = `
        <div id="lote_${loteId}" class="lote">
            <h3>Producto</h3>
            <div class="lote-row">
                <div class="input-group">
                    <label>Código del Lote:</label>
                    <input type="text" name="codigoLote" required minlength="3" maxlength="15">
                    <button type="button" class="search-button" onclick="buscarLoteManual(${loteId})">
                        <i class="bi bi-search"></i> <!-- Ícono de lupa -->
                    </button>
                </div>
                <div class="input-group">
                    <label>Cantidad:</label>
                    <input type="number" name="cantidad" required min="0" max="100000" oninput="calcularMontoLote(${loteId})">
                </div>
            </div>
            <div class="lote-row">
                <label>Precio por Unidad:</label>
                <span class="precio-text" id="precio_${loteId}">0.00</span>
            </div>
            <div class="lote-row">
                <label>Precio Total:</label>
                <span class="monto-total" id="montoTotal_${loteId}">0.00</span>
            </div>
            <div class="info-lote"></div> <!-- Contenedor para la información del lote -->
            <button type="button" class="remove-button" onclick="removeLote(${loteId})">Eliminar Lote</button>
        </div>
    `;

    loteContainer.insertAdjacentHTML("beforeend", loteHtml);
    calculateTotal();
}

function calcularMontoLote(loteId) {
    const loteDiv = document.getElementById(`lote_${loteId}`);
    const cantidadInput = loteDiv.querySelector("input[name='cantidad']");
    const precioText = loteDiv.querySelector(".precio-text");
    const montoTotalSpan = loteDiv.querySelector(".monto-total");

    const cantidad = parseFloat(cantidadInput.value) || 0;
    const cantidadDisponible = parseFloat(cantidadInput.getAttribute("data-cantidad")) || 0;

    // Validar que la cantidad no sea negativa
    if (cantidad < 0) {
        alert("La cantidad no puede ser negativa.");
        cantidadInput.value = 0;
        return;
    }

    // Validar que la cantidad no supere la cantidad disponible
    if (cantidad > cantidadDisponible) {
        alert(`La cantidad no puede ser mayor a ${cantidadDisponible}.`);
        cantidadInput.value = cantidadDisponible; // Restablecer al máximo permitido
        return;
    }

    const precio = parseFloat(precioText.textContent) || 0;
    const montoTotal = cantidad * precio;
    montoTotalSpan.textContent = montoTotal.toFixed(2); // Mostrar el monto total con 2 decimales

    calculateTotal(); // Actualizar el monto total general
}
async function buscarLoteManual(loteId) {
    const loteDiv = document.getElementById(`lote_${loteId}`);
    const codigoLoteInput = loteDiv.querySelector("input[name='codigoLote']");
    const codigoLote = codigoLoteInput.value.trim(); // Eliminar espacios en blanco

    if (codigoLote) {
        // Verificar si el código del lote ya existe en la lista
        const loteElements = document.querySelectorAll("#loteContainer > div");
        let loteExistente = false;

        loteElements.forEach((loteElement) => {
            const existingCodigoLote = loteElement.querySelector("input[name='codigoLote']").value.trim();
            if (existingCodigoLote === codigoLote && loteElement.id !== `lote_${loteId}`) {
                loteExistente = true;
            }
        });

        if (loteExistente) {
            alert('El lote ya existe en la lista. La cantidad será sumada al lote existente.');
        }

        // Buscar el lote en el servidor
        const lote = await buscarLotePorCodigo(codigoLote);
        if (lote) {
            const precioText = loteDiv.querySelector(".precio-text");
            precioText.textContent = lote.precioVenta.toFixed(2); // Mostrar el precio de venta

            const cantidadInput = loteDiv.querySelector("input[name='cantidad']");
            cantidadInput.setAttribute("data-cantidad", lote.cantidad); // Almacenar la cantidad disponible

            const infoLoteDiv = loteDiv.querySelector(".info-lote");
            infoLoteDiv.innerHTML = `
                <p><strong>Código del Lote:</strong> ${lote.codigoLote}</p>
                <p><strong>Producto:</strong> ${lote.producto.nombre} (${lote.producto.marca})</p>
                <p><strong>Descripción del Producto:</strong> ${lote.producto.descripcion}</p>
                <p><strong>Descripción del Lote:</strong> ${lote.descripcionLote}</p>
                <p><strong>Fecha de Elaboración:</strong> ${lote.fechaCreacion}</p>
                <p><strong>Fecha de Vencimiento:</strong> ${lote.fechaVencimiento}</p>
                <p><strong>Cantidad Disponible:</strong> ${lote.cantidad}</p>
            `;

            calcularMontoLote(loteId); // Calcular el monto total después de actualizar el precio
        } else {
            alert('Lote no encontrado');
        }
    } else {
        alert('Por favor, ingrese un código de lote válido.');
    }
}

async function cargarPrecioLote(input) {
    const codigoLote = input.value;
    const loteDiv = input.closest('.lote');
    const infoLoteDiv = loteDiv.querySelector(".info-lote"); // Div para mostrar la información del lote

    if (codigoLote) {
        const lote = await buscarLotePorCodigo(codigoLote);
        if (lote) {
            precioInput.value = lote.precioVenta; // Usamos el precio de venta para la venta al consumidor

            // Mostrar información adicional del lote
            infoLoteDiv.innerHTML = `
                <p><strong>Producto:</strong> ${lote.producto.nombre} (${lote.producto.marca})</p>
                <p><strong>Descripción del Lote:</strong> ${lote.descripcionLote}</p>
                <p><strong>Fecha de Creación:</strong> ${lote.fechaCreacion}</p>
                <p><strong>Fecha de Vencimiento:</strong> ${lote.fechaVencimiento}</p>
                <p><strong>Descripción del Producto:</strong> ${lote.producto.descripcion}</p>
            `;
        } else {
            precioInput.value = '';
            infoLoteDiv.innerHTML = ''; // Limpiar la información si no se encuentra el lote
            alert('Lote no encontrado');
        }
    } else {
        precioInput.value = '';
        infoLoteDiv.innerHTML = ''; // Limpiar la información si no se ingresa un código de lote
    }
    calculateTotal();
}

function removeLote(id) {
    const lote = document.getElementById(`lote_${id}`);
    if (lote) lote.remove();
    calculateTotal();
}

function calculateTotal() {
    const lotes = document.querySelectorAll("#loteContainer > div");
    let totalAmount = 0;

    lotes.forEach((lote) => {
        const montoTotalSpan = lote.querySelector(".monto-total");
        const montoTotal = parseFloat(montoTotalSpan.textContent) || 0;
        totalAmount += montoTotal;
    });

    // Actualizar el campo oculto del total
    document.getElementById("totalC").value = totalAmount.toFixed(2);

    // Actualizar el campo visible del total final
    document.getElementById("totalFinal").textContent = totalAmount.toFixed(2);
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

        const result = await response.json();

        if (response.ok) {
            mostrarMensaje(result.data, "success");
        } else {
            mostrarMensaje(result.data, "error");
        }
    } catch (error) {
        mostrarMensaje("Error al enviar la solicitud: " + error.message, "error");
    }
});

function mostrarMensaje(mensaje, tipo) {
    const mensajeElement = document.getElementById("mensaje");
    mensajeElement.textContent = mensaje;
    mensajeElement.className = tipo;
    mensajeElement.style.display = "block";
    setTimeout(() => { mensajeElement.style.display = "none"; }, 5000);
}


function mostrarMensaje(mensaje, tipo) {
    const mensajeElement = document.getElementById("mensaje");
    mensajeElement.textContent = mensaje;
    mensajeElement.className = tipo;
    mensajeElement.style.display = "block";
    setTimeout(() => {
        mensajeElement.style.display = "none";
    }, 5000);
}