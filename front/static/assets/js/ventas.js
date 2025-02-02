let detalleProductos = []; // Array para los productos seleccionados


$(document).ready(function() {
    
    // Función para manejar la búsqueda
    function manejarBusqueda() {
        console.log("Botón clickeado"); // Agrega un log para verificar
        const codigo = $("#txtcode").val().trim();
        const cantidad = parseInt($("#txtquantity").val().trim(), 10);
        if (!codigo) {
            alert("Por favor, ingrese un código válido para buscar.");
            return;
        }
        if (isNaN(cantidad) || cantidad <= 0) {
            alert("Por favor, ingrese una cantidad válida.");
            return;
        }

        $.ajax({
            url: `http://localhost:8080/myapp/lote/code/${codigo}`,
            method: "GET",
            success: function(response) {
                if (response.msg === "OK") {
                    const lote = response.lote;


                    if (cantidad > lote.cantidad) {
                        alert(`La cantidad ingresada (${cantidad}) excede la disponible (${lote.cantidad}).`);
                        return;
                    }

                    // Buscar el producto correspondiente al lote actual
                    const producto = response.listaCompleta.find(item => item.codigoLote === lote.codigoLote)?.producto;
                    
                    
                    if (producto){
                        agregarProducto({
                            id: lote.id,
                            codigoLote: lote.codigoLote,
                            descripcion: producto.descripcion,
                            cantidad: lote.cantidad,
                            precioVenta: lote.precioVenta,
                            nombre: producto.nombre, // Asignar el nombre correcto del producto
                            cant: cantidad // Agregar cantidad especificada por el usuario
                        });
                    }else{
                        alert("No se encontró un producto asociado a este lote.");
                    }
                } else {
                    alert("No se encontró el lote con el código proporcionado.");
                }
            },
            error: function(xhr) {
                alert("Error al consultar el servicio. Verifique la conexión.");
                console.error("Error:", xhr.responseText);
            }
        });
    }

    // Asociar la búsqueda al evento click del botón
    $("#btncode").on("click", manejarBusqueda);

    // Asociar la búsqueda al presionar la tecla Enter en el input
    $("#txtcode").on("keydown", function (event) {
        if (event.key === "Enter") {
            event.preventDefault(); // Evita que el formulario se envíe
            manejarBusqueda();
        }
    });
    $("#txtquantity").on("keydown", function(event) {
        if (event.key === "Enter") {
            event.preventDefault(); // Evita que el formulario se envíe
            manejarBusqueda(); // Llama a la función para agregar el producto
        }
    });
    
    // Prevenir que "Enter" en otros inputs dispare eventos no deseados
    $("input").not("#txtcode").on("keydown", function (event) {
        if (event.key === "Enter") {
            event.preventDefault(); // Detiene el comportamiento predeterminado
        }
    });
    
    

    // Función para agregar producto al detalle
    function agregarProducto(lote) {
        const productoExistente = detalleProductos.find(item => item.id === lote.id);

        if (productoExistente) {
            const nuevaCantidad = productoExistente.cant + lote.cant;
            if (nuevaCantidad > lote.cantidad) {
                alert(`La cantidad total (${nuevaCantidad}) excede la disponible (${lote.cantidad}).`);
                return;
            }
    
            productoExistente.cant = nuevaCantidad;
            productoExistente.totalPrecio = nuevaCantidad * parseFloat(lote.precioVenta);
        } else {
            detalleProductos.push({
                id: lote.id,
                codigoLote: lote.codigoLote,
                nombre: lote.nombre,
                descripcion: lote.descripcion,
                precioVenta: parseFloat(lote.precioVenta),
                precioTotal: lote.totalProductos,
                cant: lote.cant, // Usa la cantidad especificada
                totalPrecio: lote.cant * parseFloat(lote.precioVenta)
            });
        }

        calcularTotales(); // Actualizar tabla y totales
        actualizarHidden(); // Actualizar el campo oculto
    }

    // Función para calcular los totales
    function calcularTotales() {
        let subtotal = 0.0;
        let htmlVentas = "";

        detalleProductos.forEach(item => {
            const totalProductos = parseFloat(item.precioVenta) * parseInt(item.cant);
            subtotal += totalProductos;

            htmlVentas += `<tr>
                <td>
                    <button class="btn btn-warning btn-sm restar" data-id="${item.id}">-</button>
                    ${item.cant}
                    <button class="btn btn-success btn-sm aumentar" data-id="${item.id}" type="button">+</button>

                </td>
                <td>${item.codigoLote}</td>
                <td>${item.nombre}</td> 
                <td>${item.descripcion}</td>
                <td>${parseFloat(item.precioVenta).toFixed(2)}</td>
                <td>${totalProductos.toFixed(2)}</td>
                <td><button class="btn btn-danger btn-sm eliminar" data-id="${item.id}">Eliminar</button></td>
            </tr>`;

        });

        if (detalleProductos.length === 0) {
            htmlVentas = "<tr><td colspan='5'>NO HAY DATOS</td></tr>";
        }

        const iva = subtotal * 0.15;
        const total = subtotal + iva;

        $("#lotes-table-body").html(htmlVentas);
        $("#subtotal").text(`$${subtotal.toFixed(2)}`);
        $("#iva").text(`$${iva.toFixed(2)}`);
        $("#total").text(`$${total.toFixed(2)}`);

        actualizarHidden(); // Asegurarse de actualizar el hidden cada vez que cambien los totales
    }

    // Función para actualizar el campo hidden
    function actualizarHidden() {
    $("#txtdetalle").val(JSON.stringify(detalleProductos));
}

// Escucha para restar, aumentar y eliminar
$(document).on("click", ".restar", function() {
    const id = $(this).data("id");
    const producto = detalleProductos.find(item => item.id === id);
    

    if (producto) {
        producto.cant -= 1;
        if (producto.cant <= 0) {
            detalleProductos = detalleProductos.filter(item => item.id !== id); // Elimina si la cantidad es 0
        }
        calcularTotales();
    }
});

$(document).on("click", ".aumentar", function () {
    const id = $(this).data("id"); // Obtenemos el id del botón clickeado
    const producto = detalleProductos.find(item => item.id === id);

    if (!producto) return; // Si no se encuentra el producto, salimos

    const nuevaCantidad = producto.cant + 1;

    console.log(`Intentando aumentar: ${producto.nombre}, Actual: ${producto.cant}, Nueva: ${nuevaCantidad}, Disponible: ${producto.cantidad}`);

    if (nuevaCantidad > producto.cantidad) { 
        alert(`Solo hay ${producto.cantidad} disponibles.`);
        return; // Detiene la ejecución aquí
    }

    producto.cant = nuevaCantidad; // Solo actualizar si no excede el límite
    calcularTotales(); // Actualizar la tabla con los nuevos totales
});



// Función para actualizar la cantidad en la interfaz
function actualizarVista(id, cantidad) {
    $(`.producto[data-id="${id}"] .cantidad`).text(cantidad);
}




$(document).on("click", ".eliminar", function() {
    const id = $(this).data("id");
    detalleProductos = detalleProductos.filter(item => item.id !== id); // Filtra para eliminar el producto
    calcularTotales();
});

$("#btnEliminarTodos").on("click", function(event) {
    event.preventDefault();  // Evita que el formulario se envíe
    if (confirm("¿Está seguro de que desea eliminar todos los lotes seleccionados?")) {
        detalleProductos = []; // Vacía el array
        calcularTotales(); // Actualiza la tabla y totales
    }
});


    // Manejar el envío del formulario para registrar la orden
$("form").on("submit", function (event) {
    event.preventDefault();

    // Obtener y parsear los detalles desde el campo hidden
    const detalle = JSON.parse($("#txtdetalle").val());

    // Calcular subtotal
    let subtotal = 0.0;
    detalle.forEach(item => {
        const precioVenta = parseFloat(item.precioVenta) || 0; // Asegura que sea un número
        const cantidad = parseInt(item.cant) || 0; // Asegura que sea un número
        subtotal += precioVenta * cantidad;
    }); 

    const iva = subtotal * 0.15;
    const total = subtotal + iva;

    // Crear el objeto de la orden de venta
    const datosOrden = {
        subtotal: subtotal.toFixed(2),
        iva: iva.toFixed(2),
        total: total.toFixed(2),
        detalle: detalle // Usar directamente los datos del hidden
    };

    console.log("Datos enviados al backend:", datosOrden); // Para depuración

    // Enviar los datos al backend como JSON
    $.ajax({
        url: "/detalle/save",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(datosOrden),
        success: function (response) {
            console.log("Respuesta del backend:", response); // Para depuración
            alert("Orden de venta registrada correctamente.");
            window.location.href = "/ordenVenta/list";
        },
        error: function (xhr) {
            console.error("Error al registrar el detalle de venta:", xhr.responseText); // Para depuración
            alert("Error al registrar la orden de venta.");
        }
    });
});

    
});
