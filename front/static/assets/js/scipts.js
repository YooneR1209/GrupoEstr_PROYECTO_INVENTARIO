// const searchInput = document.getElementById('searchProducto');
// const selectContainer = document.getElementById('selectContainer');
// const productoSelect = document.getElementById('productoSelect');

// searchInput.addEventListener('focus', function () {
//   selectContainer.classList.remove('hidden');
// });

// document.addEventListener('click', function (event) {
//   if (!selectContainer.contains(event.target) && event.target !== searchInput) {
//     selectContainer.classList.add('hidden');
//   }
// });

// productoSelect.addEventListener('click', function(event) {
//   const selectedOption = event.target;
  
//   if (selectedOption.tagName === 'OPTION') {
//     searchInput.value = selectedOption.textContent.trim();

//     selectContainer.classList.add('hidden');
//   }
// });

//

// const cuadroProducto = document.getElementById('cuadroProducto');
// const cuadroLote = document.getElementById('cuadroLote');

// // Establecer el cuadro de lote oculto al principio
// cuadroLote.classList.add('hidden');

// // Mostrar el cuadro de lote al seleccionar un producto
// productoSelect.addEventListener('change', function () {
//   if (productoSelect.value !== "") {
//     cuadroLote.classList.remove('hidden');
//   }
// });

// // Ocultar el cuadro de lote si se hace clic fuera
// document.addEventListener('click', function (event) {
//   if (productoSelect.value == "") {
//     cuadroLote.classList.add('hidden');
//   }
// });

// //


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

    function addLote() {
        loteId++;
        const loteContainer = document.getElementById("loteContainer");
        const loteHtml = `
            <div id="lote_${loteId}">
                <h3>Lote ${loteId}</h3>
                <label>Fecha de Vencimiento:</label>
                <input type="date" name="fechaVencimiento"><br>
                <label>Código del Lote:</label>
                <input type="text" name="codigoLote"><br>
                <label>Precio de Compra:</label>
                <input type="number" name="precioCompra" step="0.01"><br>
                <label>Fecha de Creación:</label>
                <input type="date" name="fechaCreacion"><br>
                <label>Descripción:</label>
                <input type="text" name="descripcionLote"><br>
                <label>ID1:</label>
                <input type="number" name="id_Producto"><br>
                <label>Cantidad:</label>
                <input type="number" name="cantidad"><br>
                <label>Precio de Venta:</label>
                <input type="number" name="precioVenta" step="0.01"><br>
                <button type="button" onclick="removeLote(${loteId})">Eliminar Lote</button><br><br>
            </div>
        `;
        loteContainer.insertAdjacentHTML("beforeend", loteHtml);
    }

    function removeLote(id) {
        const lote = document.getElementById(`lote_${id}`);
        lote.remove();
    }

    document.getElementById("ordenCompraForm").addEventListener("submit", function (event) {
        event.preventDefault(); // Evitar el envío estándar del formulario.

        const formData = new FormData(event.target);
        const data = {
            nro_OrdenCompra: formData.get("nro_Oc"),
            fechaCompra: formData.get("fechaCom"),
            cedula_Distribuidor: formData.get("cedula_dis"),
            loteList: [], // Esto debe ser un arreglo de objetos correctamente serializado
        };
        
        // Procesar lotes
        const lotes = document.querySelectorAll("#loteContainer > div");
        lotes.forEach((lote) => {
            const loteData = {};
            lote.querySelectorAll("input").forEach((input) => {
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
