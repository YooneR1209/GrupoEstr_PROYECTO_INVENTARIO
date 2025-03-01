import json

from flask import (
    Flask,
    Blueprint,
    flash,
    render_template,
    request,
    redirect,
    url_for,
    session,
    get_flashed_messages,
)

import requests
from flask_cors import CORS

router = Blueprint("router", __name__)


@router.route("/")
def home():
    if "token" in session:
        return redirect(url_for("router.login"))
    return render_template(
        "template.html",
        usuario=session.get("usuario"),
        idPersona=session.get("idPersona"),
    )  # Página de inicio o bienvenida


@router.route("/home")
def homeo():
    return render_template("inicio.html")  # Página de inicio o bienvenida


@router.route("/inventario")
def inventario():
    return render_template("fragmento/inventario.html")  # Ruta de inventario


# Ruta para mostrar el formulario de login
@router.route("/login")
def login():
    return render_template("modulologin/iniciosesion.html")


# Ruta para manejar el envío del formulario de login
@router.route("/iniciosesion", methods=["POST"])
def procesar_login():
    header = {"Content-Type": "application/json"}
    form = request.form
    data = {"correo": form["correo"], "clave": form["clave"]}

    r = requests.post(
        "http://localhost:8080/myapp/persona/iniciosesion", headers=header, json=data
    )
    dat = r.json()

    if r.status_code == 200:
        session["token"] = dat["token"]
        session["usuario"] = form["correo"]
        session["idPersona"] = dat["idPersona"]
        return redirect(
            url_for("router.dashboard")
        )  # Redirige al dashboard si login exitoso
    else:
        error = r.json().get("error", "Error al iniciar sesión")
        return render_template("modulologin/iniciosesion.html", error=error)


# Ruta del dashboard
@router.route("/navegacion")
def dashboard():
    if "token" not in session:
        return redirect(url_for("router.login"))  # Redirige al login si no hay token

    # Obtener los mensajes flash
    success_message = get_flashed_messages(category_filter="success")

    return render_template(
        "modulologin/datos.html",
        usuario=session.get("usuario"),
        idPersona=session.get("idPersona"),
        success_message=success_message,
    )  # Pasar el mensaje de éxito a la plantilla


# Ruta para logout y eliminar la sesión
@router.route("/logout")
def logout():
    session.clear()  # Elimina todos los datos de la sesión
    return redirect(url_for("router.login"))  # Redirige al login


@router.route("/registro", methods=["POST"])
def registro():
    headers = {"Content-Type": "application/json"}

    form = request.form

    datar = {
        "nombre": form["nombre"],
        "apellido": form["apellido"],
        "correo": form["correo"],
        "telefono": form["telefono"],
        "dni": form["dni"],
        "clave": form["clave"],
    }

    r = requests.post(
        "http://localhost:8080/myapp/persona/save",
        headers=headers,
        data=json.dumps(datar),
    )

    dat = r.json()

    if r.status_code == 200:
        if "token" in dat:
            session["token"] = dat["token"]
            session["usuario"] = datar["correo"]
            flash(
                "¡Registro exitoso! Ahora puedes iniciar sesión.", category="success"
            )  # Mensaje de éxito
            return redirect(url_for("router.dashboard"))
        else:
            return render_template(
                "modulologin/registro.html",
                error_message=dat.get(
                    "message", "No se recibió el token en la respuesta del servidor"
                ),
            )
    else:
        return render_template(
            "modulologin/registro.html",
            error_message=dat.get("message", "Error al registrar"),
        )


@router.route("/mipersona/<id>")
def mipersona(id):
    r = requests.get("http://localhost:8080/myapp/persona/list/" + id)
    dat = r.json()
    if r.status_code == 200:
        return render_template("modulologin/perfil.html", lista=dat["data"])
    else:
        return render_template("modulologin/perfil.html", lista=dat["data"])


@router.route("/mipersona/actualizar", methods=["POST"])
def actualizar():
    headers = {"Content-Type": "application/json"}
    form = request.form

    dataF = {
        "idPersona": form["id"],
        "nombre": form["nombre"],
        "apellido": form["apellido"],
        "correo": form["correo"],
        "telefono": form["telefono"],
        "dni": form["dni"],
        "clave": form["clave"],
    }

    r = requests.post(
        "http://localhost:8080/myapp/persona/actualizar",
        data=json.dumps(dataF),
        headers=headers,
    )
    dat = r.json()
    if r.status_code == 200:
        return redirect(
            url_for("router.mipersona", id=dataF["idPersona"], lista=dat["data"])
        )
    else:
        return render_template("modulologin/perfil.html", lista=dat["data"])


@router.route("/formularegistro")
def formularegistro():

    return render_template("modulologin/registro.html")


# INICIO PRODUCTO


@router.route("/producto/list")
def list_producto(msg=""):
    if "token" not in session:
        return redirect(url_for("router.login"))
    r_producto = requests.get("http://localhost:8080/myapp/producto/list")
    data_producto = r_producto.json()

    print(data_producto)

    return render_template(
        "moduloproducto/producto.html",
        lista_producto=data_producto["data"],
        usuario=session.get("usuario"),
        idPersona=session.get("idPersona"),
    )


@router.route("/lote/list")
def list_lote(msg=""):
    if "token" not in session:
        return redirect(url_for("router.login"))

    r_lote = requests.get("http://localhost:8080/myapp/lote/list")
    data_lote = r_lote.json()

    print(data_lote)

    return render_template(
        "modulolote/lote.html",
        lista_lote=data_lote["data"],
        usuario=session.get("usuario"),
        idPersona=session.get("idPersona"),
    )


@router.route("/lote/register")
def view_register_lote():
    if "token" not in session:
        return redirect(url_for("router.login"))

    r_lote = requests.get("http://localhost:8080/myapp/lote/list")
    data_lote = r_lote.json()
    r_producto = requests.get("http://localhost:8080/myapp/producto/list")
    data_producto = r_producto.json()

    return render_template(
        "modulolote/registro.html",
        lista_lote=data_lote["data"],
        lista_producto=data_producto["data"],
        usuario=session.get("usuario"),
        idPersona=session.get("idPersona"),
    )


@router.route("/lote/save", methods=["POST"])
def save_lote():
    headers = {"Content-Type": "application/json"}
    form = request.form

    data_lote = {
        "codigoLote": form["codigol"],
        "cantidad": form["cant"],
        "precioCompra": form["precioc"],
        "precioVenta": form["preciov"],
        "fechaVencimiento": form["fechav"],
        "fechaCreacion": form["fechac"],
        "descripcionLote": form["descripcionl"],
        "producto": form["produc"],
    }

    r_lote = requests.post(
        "http://localhost:8080/myapp/lote/save",
        data=json.dumps(data_lote),
        headers=headers,
    )  # Hacer la petición para guardar la lote

    if r_lote.status_code == 200:

        flash("Registro guardado correctamente", category="info")
        return redirect("/lote/list")
    else:
        flash(r_lote.json().get("data", "Error al guardar la lote"), category="error")
        return redirect("/lote/list")


@router.route("/lote/order/<atributo>/<tipo>")
def view_order_lote(atributo, tipo):
    url = f"http://localhost:8080/myapp/lote/list/order/{atributo}/{tipo}"
    r = requests.get(url)
    print("url: " + url)
    if r.status_code == 200:
        data1 = r.json()
        print(data1)
        return render_template(
            "modulolote/lote.html",
            lista_lote=data1["data"],
            atributo_actual=atributo,
            tipo_actual=tipo,
        )
    else:
        return render_template(
            "modulolote/lote.html",
            lista_lote=[],
            message="No se pudo obtener los lotes ordenados",
        )


@router.route("/lote/search/codigoLote/<texto>")
def view_buscar_lote(texto):
    url = f"http://localhost:8080/myapp/lote/list/search/codigoLote/{texto}"  # Usar f-string para incluir el texto
    r = requests.get(url)
    data1 = r.json()
    print(f"Response JSON: {data1}")
    if r.status_code == 200:
        if isinstance(data1["data"], dict):  # Verificar si es un diccionario
            lista = [data1["data"]]  # Convertir a lista
        else:
            lista = data1["data"]  # Usar la lista directamente
        return render_template("modulolote/lote.html", lista_lote=lista)
    else:
        return render_template(
            "modulolote/lote.html",
            lista_lote=[],
            message="No existe el elemento",
        )


@router.route("/ordenCompra/order/<atributo>/<tipo>")
def view_order_ordenCompra(atributo, tipo):
    url = f"http://localhost:8080/myapp/ordenCompra/list/order/{atributo}/{tipo}"
    r = requests.get(url)
    print("url: " + url)
    if r.status_code == 200:
        data1 = r.json()
        print(data1)
        return render_template(
            "modulocompra/compras.html",
            lista_ordenCompra=data1["data"],
            atributo_actual=atributo,
            tipo_actual=tipo,
        )
    else:
        return render_template(
            "modulocompra/compras.html",
            lista_ordenCompra=[],
            message="No se pudo obtener los ordenCompras ordenados",
        )


@router.route("/ordenVenta/order/<atributo>/<tipo>")
def view_order_ordenVenta(atributo, tipo):
    url = f"http://localhost:8080/myapp/ordenVenta/list/order/{atributo}/{tipo}"
    r = requests.get(url)
    print("url: " + url)
    if r.status_code == 200:
        data1 = r.json()
        print(data1)
        return render_template(
            "moduloventa/ventas.html",
            lista_ordenVenta=data1["data"],
            atributo_actual=atributo,
            tipo_actual=tipo,
        )
    else:
        return render_template(
            "moduloventa/ventas.html",
            lista_ordenVenta=[],
            message="No se pudo obtener los ordenVentas ordenados",
        )


@router.route("/producto/order/<atributo>/<tipo>")
def view_order_producto(atributo, tipo):
    url = f"http://localhost:8080/myapp/producto/list/order/{atributo}/{tipo}"
    r = requests.get(url)
    print("url: " + url)
    if r.status_code == 200:
        data1 = r.json()
        print(data1)
        return render_template(
            "moduloproducto/producto.html",
            lista_producto=data1["data"],
            atributo_actual=atributo,
            tipo_actual=tipo,
        )
    else:
        return render_template(
            "moduloproducto/producto.html",
            lista_producto=[],
            message="No se pudo obtener los productos ordenados",
        )


@router.route("/ordenVenta/search/nro_OrdenVenta/<texto>")
def view_buscar_ordenVenta(texto):
    url = f"http://localhost:8080/myapp/ordenVenta/list/search/nro_OrdenVenta/{texto}"  # Usar f-string para incluir el texto
    r = requests.get(url)
    data1 = r.json()
    print(f"Response JSON: {data1}")
    if r.status_code == 200:
        if isinstance(data1["data"], dict):  # Verificar si es un diccionario
            lista = [data1["data"]]  # Convertir a lista
        else:
            lista = data1["data"]  # Usar la lista directamente
        return render_template("moduloventa/ventas.html", lista_ordenVenta=lista)
    else:
        return render_template(
            "moduloventa/ventas.html",
            lista_ordenVenta=[],
            message="No existe el elemento",
        )


@router.route("/ordenCompra/search/codigoLote/<texto>")
def view_buscar_ordenCompra(texto):
    url = f"http://localhost:8080/myapp/ordenCompra/list/search/nro_OrdenCompra/{texto}"  # Usar f-string para incluir el texto
    r = requests.get(url)
    data1 = r.json()
    print(f"Response JSON: {data1}")
    if r.status_code == 200:
        if isinstance(data1["data"], dict):  # Verificar si es un diccionario
            lista = [data1["data"]]  # Convertir a lista
        else:
            lista = data1["data"]  # Usar la lista directamente
        return render_template("modulocompra/compras.html", lista_ordenCompra=lista)
    else:
        return render_template(
            "modulocompra/compras.html",
            lista_ordenCompra=[],
            message="No existe el elemento",
        )


@router.route("/producto/search/nombre/<texto>")
def view_buscar_producto(texto):
    url = f"http://localhost:8080/myapp/producto/list/search/nombre/{texto}"  # Usar f-string para incluir el texto
    r = requests.get(url)
    data1 = r.json()
    print(f"Response JSON: {data1}")
    if r.status_code == 200:
        if isinstance(data1["data"], dict):  # Verificar si es un diccionario
            lista = [data1["data"]]  # Convertir a lista
        else:
            lista = data1["data"]  # Usar la lista directamente
        return render_template("moduloproducto/producto.html", lista_producto=lista)
    else:
        return render_template(
            "moduloproducto/producto.html",
            lista_producto=[],
            message="No existe el elemento",
        )


@router.route("/lote/update", methods=["POST"])
def update_lote():
    headers = {"Content-Type": "application/json"}
    form = request.form

    data_lote = {
        "id": form["id"],
        "codigoLote": form["codigol"],
        "cantidad": form["cant"],
        "precioCompra": form["precioc"],
        "precioVenta": form["preciov"],
        "fechaVencimiento": form["fechav"],
        "fechaCreacion": form["fechac"],
        "descripcionLote": form["descripcionl"],
    }

    r_lote = requests.post(
        "http://localhost:8080/myapp/lote/update",
        data=json.dumps(data_lote),
        headers=headers,
    )

    if r_lote.status_code == 200:

        flash("Registro actualizado correctamente", category="info")
        return redirect("/lote/list")
    else:
        flash(r_lote.json().get("data", "Error al guardar la lote"), category="error")
        return redirect("/lote/list")


@router.route("/lote/edit/<id>", methods=["GET"])
def view_edit_lote(id):
    if "token" not in session:
        return redirect(url_for("router.login"))

    r = requests.get("http://localhost:8080/myapp/lote/listType")
    lista_tipos = r.json()  # Guardamos la respuesta JSON

    r1 = requests.get(
        f"http://localhost:8080/myapp/lote/get/{id}"
    )  # Obtenemos los datos de la lote por ID

    if r1.status_code == 200:
        data_lote = r1.json()
        lote = data_lote["data"]
        print(f"Datos del lote: {lote}")

        return render_template(
            "modulolote/edit.html",
            lista=lista_tipos["data"],
            lote=lote,
            usuario=session.get("usuario"),
            idPersona=session.get("idPersona"),
        )

    else:
        flash("Error al obtener la lote", category="error")
        return redirect("/admin/lote/list")


@router.route("/lote/delete/<int:id>", methods=["POST"])
def delete_lote(id):
    if "token" not in session:
        return redirect(url_for("router.login"))

    r = requests.post(f"http://localhost:8080/myapp/lote/delete/{id}")

    if r.status_code == 200:

        flash("Lote eliminado con éxito", category="info")
        return redirect("/lote/list")
    else:
        flash(
            r.json().get("data", "Error al eliminar el lote"),
            category="error",
        )
        return redirect("/lote/list")


@router.route("/producto/register")
def view_register_producto():
    if "token" not in session:
        return redirect(url_for("router.login"))
    r_producto = requests.get("http://localhost:8080/myapp/producto/list")
    data_producto = r_producto.json()

    return render_template(
        "moduloproducto/registro.html",
        lista_producto=data_producto["data"],
        usuario=session.get("usuario"),
        idPersona=session.get("idPersona"),
    )


@router.route("/producto/save", methods=["POST"])
def save_producto():
    headers = {"Content-Type": "application/json"}
    form = request.form

    data_producto = {
        "nombre": form["nom"],
        "tipoProducto": form["tipop"],
        "marca": form["marca"],
        "descripcion": form["descripcion"],
    }

    r_producto = requests.post(
        "http://localhost:8080/myapp/producto/save",
        data=json.dumps(data_producto),
        headers=headers,
    )  # Hacer la petición para guardar la producto

    if r_producto.status_code == 200:

        flash("Registro guardado correctamente", category="info")
        return redirect("/producto/list")
    else:
        flash(
            r_producto.json().get("data", "Error al guardar el producto"),
            category="error",
        )
        return redirect("/producto/list")


@router.route("/producto/delete/<int:id>", methods=["POST"])
def delete_producto(id):

    r = requests.post(f"http://localhost:8080/myapp/producto/delete/{id}")

    if r.status_code == 200:

        flash("Producto eliminado con éxito", category="info")
        return redirect("/producto/list")
    else:
        flash(
            r.json().get("data", "Error al eliminar el producto"),
            category="error",
        )
        return redirect("/producto/list")


@router.route("/producto/edit/<id>", methods=["GET"])
def view_edit_producto(id):
    if "token" not in session:
        return redirect(url_for("router.login"))

    r = requests.get("http://localhost:8080/myapp/producto/listType")
    lista_tipos = r.json()  # Guardamos la respuesta JSON

    r1 = requests.get(
        f"http://localhost:8080/myapp/producto/get/{id}"
    )  # Obtenemos los datos de la producto por ID

    if r1.status_code == 200:
        data_producto = r1.json()
        producto = data_producto["data"]

        return render_template(
            "moduloproducto/edit.html", lista=lista_tipos["data"], producto=producto
        )

    else:
        flash("Error al obtener la producto", category="error")
        return redirect("/admin/producto/list")


@router.route("/producto/update", methods=["POST"])
def update_producto():
    headers = {"Content-Type": "application/json"}
    form = request.form

    data_producto = {
        "id": form["id"],
        "nombre": form["nom"],
        "tipoProducto": form["tipop"],
        "marca": form["marca"],
        "descripcion": form["descripcion"],
    }

    r_producto = requests.post(
        "http://localhost:8080/myapp/producto/update",
        data=json.dumps(data_producto),
        headers=headers,
    )

    if r_producto.status_code == 200:

        flash("Registro actualizado correctamente", category="info")
        return redirect("/producto/list")
    else:
        flash(
            r_producto.json().get("data", "Error al guardar la producto"),
            category="error",
        )
        return redirect("/producto/list")


@router.route("/producto/list/lote/<int:producto_id>")
def list_lote_producto(producto_id, msg=""):
    r_lote = requests.get(
        f"http://localhost:8080/myapp/producto/list/search/id/lote/{producto_id}"
    )

    if r_lote.status_code == 200:
        data_lote = r_lote.json()  # Parsear el JSON recibido
        print(data_lote)  # Para depuración, puedes imprimir los datos obtenidos
        return render_template(
            "modulolote/lotes_producto.html",
            data_lote=data_lote["data"],
            lista_lote=data_lote["data"]["lotes"],
        )


# OrdenVenta
@router.route("/ordenVenta/list")
def list_Venta(msg=""):
    r_OrdenVenta = requests.get("http://localhost:8080/myapp/ordenVenta/list")
    data_Venta = r_OrdenVenta.json()
    print(data_Venta)
    return render_template(
        "moduloVenta/OrdenVenta.html", lista_Venta=data_Venta["data"]
    )


@router.route("/ordenVenta/register")
def view_register_ordenVenta():
    r_ordenVenta = requests.get("http://localhost:8080/myapp/ordenVenta/list")
    data_ordenVenta = r_ordenVenta.json()
    return render_template(
        "moduloVenta/registroVenta.html", lista_producto=data_ordenVenta["data"]
    )


@router.route("/ordenVenta/save", methods=["POST"])
def save_OrdenVenta():
    headers = {"Content-Type": "application/json"}
    form = request.form
    detalle = form["txtdetalle"][:-1]
    lista = detalle.split(":")
    details = []
    for item in lista:
        lista_aux = item.split(",")
        details.append(
            {
                "service": lista_aux[0],
                "cant": lista_aux[1],
                "precioUnitario": lista_aux[2],
                "totalVenta": (float(lista_aux[1]) * float(lista_aux[2])),
            }
        )
    dataF = {
        "n_ordenVenta": form["n_ord"],
        "iva": form["iva"][1:],
        "subtotalVenta": form["sub"][1:],
        "totalVenta": form["total"][1:],
        "details": details,
    }
    print(dataF)

    r_ordenVenta = requests.post(
        "http://localhost:8080/myapp/ordenVenta/save",
        data=json.dumps(dataF),
        headers=headers,
    )  # Hacer la petición para guardar la producto

    dat = r_ordenVenta.json()
    if r_ordenVenta.status_code == 200:
        flash("Registro guardado correctamente", category="info")
        return redirect("/ordenVenta/list")
    else:
        flash(
            r_ordenVenta.json().get("data", "Error al guardar la orden de venta"),
            category="error",
        )
        return redirect("/ordenVenta/list")


# DetalleOrdenVenta
@router.route("/detalleVenta/list")
def list_DetalleVenta(msg=""):
    r_DetalleVenta = requests.get("http://localhost:8080/myapp/detalleVenta/list")
    data_DetalleVenta = r_DetalleVenta.json()
    print(data_DetalleVenta)
    return render_template(
        "moduloVenta/DetalleOrdenVenta.html",
        lista_DetalleVenta=data_DetalleVenta["data"],
    )


@router.route("/detalleVenta/register")
def view_register_detalleVenta():
    r_detalleVenta = requests.get("http://localhost:8080/myapp/detalleVenta/list")
    data_detalleVenta = r_detalleVenta.json()
    return render_template(
        "moduloVenta/registroDetalleVenta.html", lista_detalle=data_detalleVenta["data"]
    )


@router.route("/detalleVenta/save", methods=["POST"])
def save_DetalleVenta():
    headers = {"Content-Type": "application/json"}
    form = request.form
    data_detalleVenta = {
        "cantidadProducto": form["cant"],
        "precioUnitario": form["prec"],
        "precioTotal": form["total"],
        "producto": form["pro"],
    }
    r_detalleVenta = requests.post(
        "http://localhost:8080/myapp/detalleVenta/save",
        data=json.dumps(data_detalleVenta),
        headers=headers,
    )  # Hacer la petición para guardar la producto

    if r_detalleVenta.status_code == 200:
        flash("Registro guardado correctamente", category="info")
        return redirect("/detalleVenta/list")
    else:
        flash(
            r_detalleVenta.json().get("data", "Error al guardar la orden de venta"),
            category="error",
        )
        return redirect("/detalleVenta/list")


# DISTRIBUIDOR


@router.route("/distribuidor/list")
def list_distribuidor(msg=""):
    if "token" not in session:
        return redirect(url_for("router.login"))
    r_distribuidor = requests.get("http://localhost:8080/myapp/distribuidor/list")
    data_distribuidor = r_distribuidor.json()

    print(data_distribuidor)

    return render_template(
        "modulodistribuidor/distribuidor.html",
        lista_distribuidor=data_distribuidor["data"],
        usuario=session.get("usuario"),
        idPersona=session.get("idPersona"),
    )


@router.route("/distribuidor/register")
def view_register_distribuidor():
    if "token" not in session:
        return redirect(url_for("router.login"))

    r_distribuidor = requests.get("http://localhost:8080/myapp/distribuidor/list")
    data_distribuidor = r_distribuidor.json()

    # Obtener el mensaje de error de la sesión si existe
    error_msg = session.pop("error_msg", None)

    return render_template(
        "modulodistribuidor/registro.html",
        lista_distribuidor=data_distribuidor["data"],
        usuario=session.get("usuario"),
        idPersona=session.get("idPersona"),
        error_msg=error_msg,  # Pasar el mensaje de error a la plantilla
    )


@router.route("/distribuidor/save", methods=["POST"])
def save_distribuidor():
    headers = {"Content-Type": "application/json"}
    form = request.form

    data_distribuidor = {
        "nombre": form["nom"],
        "cedula": form["ced"],
        "celular": form["cel"],
        "descripcion": form["descripcion"],
    }

    r_distribuidor = requests.post(
        "http://localhost:8080/myapp/distribuidor/save",
        data=json.dumps(data_distribuidor),
        headers=headers,
    )  # Hacer la petición para guardar el distribuidor

    if r_distribuidor.status_code == 200:
        flash("Registro guardado correctamente", category="success")
        return redirect("/distribuidor/list")
    else:
        # Obtener el mensaje de error de la respuesta JSON
        error_message = r_distribuidor.json().get(
            "data", "Error al guardar el distribuidor"
        )
        flash(error_message, category="error")
        return redirect("/distribuidor/register")  # Redirigir al formulario de registro


@router.route("/distribuidor/delete/<int:id>", methods=["POST"])
def delete_distribuidor(id):

    r_distribuidor = requests.post(
        f"http://localhost:8080/myapp/distribuidor/delete/{id}"
    )

    if r_distribuidor.status_code == 200:

        flash("Distribuidor eliminado con éxito", category="info")
        return redirect("/distribuidor/list")
    else:
        flash(
            r_distribuidor.json().get("data", "Error al eliminar la distribuidor"),
            category="error",
        )
        return redirect("/distribuidor/list")


@router.route("/distribuidor/edit/<id>", methods=["GET"])
def view_edit_distribuidor(id):
    if "token" not in session:
        return redirect(url_for("router.login"))

    r = requests.get("http://localhost:8080/myapp/distribuidor/listType")
    lista_tipos = r.json()  # Guardamos la respuesta JSON

    r1 = requests.get(
        f"http://localhost:8080/myapp/distribuidor/get/{id}"
    )  # Obtenemos los datos de la distribuidor por ID

    if r1.status_code == 200:
        data_distribuidor = r1.json()
        distribuidor = data_distribuidor["data"]

        return render_template(
            "modulodistribuidor/edit.html",
            lista=lista_tipos["data"],
            distribuidor=distribuidor,
        )

    else:
        flash("Error al obtener la distribuidor", category="error")
        return redirect("/admin/distribuidor/list")


@router.route("/distribuidor/update", methods=["POST"])
def update_distribuidor():
    headers = {"Content-Type": "application/json"}
    form = request.form

    data_distribuidor = {
        "id": form["id"],
        "nombre": form["nom"],
        "cedula": form["ced"],
        "celular": form["cel"],
        "descripcion": form["descripcion"],
    }

    r_distribuidor = requests.post(
        "http://localhost:8080/myapp/distribuidor/update",
        data=json.dumps(data_distribuidor),
        headers=headers,
    )

    if r_distribuidor.status_code == 200:

        flash("Distribuidor actualizado correctamente", category="info")
        return redirect("/distribuidor/list")
    else:
        flash(
            r_distribuidor.json().get("data", "Error al guardar la distribuidor"),
            category="error",
        )
        return redirect("/distribuidor/list")


# COMPRAS


@router.route("/compras/list")
def list_ordenCompra(msg=""):
    if "token" not in session:
        return redirect(url_for("router.login"))

    r_ordenCompra = requests.get("http://localhost:8080/myapp/ordenCompra/list")
    data_ordenCompra = r_ordenCompra.json()

    print(data_ordenCompra)

    return render_template(
        "modulocompra/compras.html",
        lista_ordenCompra=data_ordenCompra["data"],
    )


@router.route("/ventas/list")
def list_ordenVenta(msg=""):
    if "token" not in session:
        return redirect(url_for("router.login"))

    r_ordenVenta = requests.get("http://localhost:8080/myapp/ordenVenta/list")
    data_ordenVenta = r_ordenVenta.json()

    print(data_ordenVenta)

    return render_template(
        "moduloventa/ventas.html",
        lista_ordenVenta=data_ordenVenta["data"],
    )


@router.route("/compras/register")
def list_compras(msg=""):
    if "token" not in session:
        return redirect(url_for("router.login"))

    r_distribuidor = requests.get("http://localhost:8080/myapp/distribuidor/list")
    data_distribuidor = r_distribuidor.json()

    r_producto = requests.get("http://localhost:8080/myapp/producto/list")
    data_producto = r_producto.json()

    print(data_distribuidor)

    return render_template(
        "modulocompra/registro.html",
        lista_distribuidor=data_distribuidor["data"],
        lista_producto=data_producto["data"],
        usuario=session.get("usuario"),
        idPersona=session.get("idPersona"),
    )


@router.route("/ventas/register")
def list_ventas(msg=""):
    if "token" not in session:
        return redirect(url_for("router.login"))

    r_distribuidor = requests.get("http://localhost:8080/myapp/distribuidor/list")
    data_distribuidor = r_distribuidor.json()

    r_producto = requests.get("http://localhost:8080/myapp/producto/list")
    data_producto = r_producto.json()

    print(data_distribuidor)

    return render_template(
        "moduloventa/registro.html",
        lista_distribuidor=data_distribuidor["data"],
        lista_producto=data_producto["data"],
        usuario=session.get("usuario"),
        idPersona=session.get("idPersona"),
    )


@router.route("/compra/save", methods=["POST"])
def save_compra():
    headers = {"Content-Type": "application/json"}
    form = request.form

    data_ordenCompra = {
        "nro_OrdenCompra": form.get("nro_Oc"),
        "fechaCompra": form.get("fechaCom", None),
        "cedula_Distribuidor": form.get("cedula_dis"),
        "loteList": form.get("loteList"),
        "totalCompra": "30",
    }

    print(data_ordenCompra)  # Imprime todo el diccionario recibido

    return "Se agregooo", 200

    # r_ordenCompra = requests.post(
    #     "http://localhost:8080/myapp/ordenCompra/save",
    #     data=json.dumps(data_ordenCompra),
    #     headers=headers,
    # )  # Hacer la petición para guardar la ordenCompra

    # if r_ordenCompra.status_code == 200:

    #     flash("Registro guardado correctamente", category="info")
    #     return redirect("/ordenCompra/list")
    # else:
    #     flash(
    #         r_ordenCompra.json().get("data", "Error al guardar la ordenCompra"),
    #         category="error",
    #     )
    #     return redirect("/ordenCompra/list")


@router.route("/ordenCompra/imprimir/<id>", methods=["GET"])
def view_edit_ordenCompra(id):
    if "token" not in session:
        return redirect(url_for("router.login"))

    r = requests.get("http://localhost:8080/myapp/ordenCompra/listType")
    lista_tipos = r.json()  # Guardamos la respuesta JSON

    r1 = requests.get(
        f"http://localhost:8080/myapp/ordenCompra/get/{id}"
    )  # Obtenemos los datos de la ordenCompra por ID

    if r1.status_code == 200:
        data_ordenCompra = r1.json()
        ordenCompra = data_ordenCompra["data"]

        return render_template(
            "modulocompra/imprimir.html",
            lista=lista_tipos["data"],
            ordenCompra=ordenCompra,
        )

    else:
        flash("Error al obtener la ordenCompra", category="error")
        return redirect("/admin/ordenCompra/list")


@router.route("/ordenVenta/imprimir/<id>", methods=["GET"])
def view_edit_ordenVenta(id):
    if "token" not in session:
        return redirect(url_for("router.login"))

    r = requests.get("http://localhost:8080/myapp/ordenVenta/listType")
    lista_tipos = r.json()  # Guardamos la respuesta JSON

    r1 = requests.get(
        f"http://localhost:8080/myapp/ordenVenta/get/{id}"
    )  # Obtenemos los datos de la ordenVenta por ID

    if r1.status_code == 200:
        data_ordenVenta = r1.json()
        ordenVenta = data_ordenVenta["data"]

        return render_template(
            "moduloventa/imprimir.html",
            lista=lista_tipos["data"],
            ordenVenta=ordenVenta,
        )

    else:
        flash("Error al obtener la ordenVenta", category="error")
        return redirect("/admin/ordenVenta/list")
