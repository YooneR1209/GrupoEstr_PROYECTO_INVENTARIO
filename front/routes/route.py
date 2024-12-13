import json
from flask import Flask, Blueprint, flash, render_template, request, redirect, url_for, session
import requests

router = Blueprint('router', __name__)

@router.route('/')
def home():
    if 'token' in session:
        return redirect(url_for('router.login'))
    return render_template('template.html', usuario=session.get('usuario'), idPersona=session.get('idPersona'))  # Página de inicio o bienvenida

@router.route('/home')
def homeo():
    return render_template('inicio.html')  # Página de inicio o bienvenida

@router.route('/inventario')
def inventario():
    return render_template('fragmento/inventario.html')  # Ruta de inventario

# Ruta para mostrar el formulario de login
@router.route('/login')
def login():
    return render_template('modulologin/iniciosesion.html')

# Ruta para manejar el envío del formulario de login
@router.route('/iniciosesion', methods=['POST'])
def procesar_login():
    header = {
        "Content-Type": "application/json"
    }
    form = request.form
    data = {
        "correo": form["correo"],
        "clave": form["clave"]
    }

    r = requests.post("http://localhost:8080/myapp/persona/iniciosesion", headers=header, json=data)
    dat = r.json()

    if r.status_code == 200:
        session['token'] = dat["token"]
        session['usuario'] = form["correo"]
        session["idPersona"] = dat["idPersona"]
        return redirect(url_for('router.dashboard'))  # Redirige al dashboard si login exitoso
    else:
        error = r.json().get("error", "Error al iniciar sesión")
        return render_template('modulologin/iniciosesion.html', error=error)

# Ruta del dashboard
@router.route('/navegacion')
def dashboard():
    if 'token' not in session:
        return redirect(url_for('router.login'))  # Redirige al login si no hay token
    return render_template('modulologin/datos.html', usuario=session.get('usuario'), idPersona=session.get('idPersona'))

# Ruta para logout y eliminar la sesión
@router.route('/logout')
def logout():
    session.clear()  # Elimina todos los datos de la sesión
    return redirect(url_for('router.login'))  # Redirige al login

@router.route('/registro' , methods=['POST'])
def registro():
    hearders = {'Content-Type': 'application/json'}
    form = request.form
    
    datar = {
        "nombre": form["nombre"],
        "apellido": form["apellido"],
        "correo": form["correo"],
        "telefono": form["telefono"],
        "dni": form["dni"],
        "clave": form["clave"]	
    }

    r = requests.post("http://localhost:8080/myapp/persona/save", headers=hearders, data=json.dumps(datar))
    dat = r.json()
    if r.status_code == 200:
        if "token" in dat:
            session['token'] = dat["token"]
            session['usuario'] = datar["correo"]
            return redirect(url_for('router.dashboard'))
        else:
            error = dat.get("message", "No se recibió el token en la respuesta del servidor")
            return render_template('modulologin/registro.html', error=error)
    else:
        error = dat.get("message", "Error al registrar")
        return render_template('modulologin/registro.html', error=error)


@router.route('/mipersona/<id>')
def mipersona(id):
    r = requests.get("http://localhost:8080/myapp/persona/list/"+id)
    dat = r.json()
    if(r.status_code == 200):
        return render_template('modulologin/perfil.html', lista=dat["data"])
    else:
       return render_template('modulologin/perfil.html', lista=dat["data"])
    
@router.route('/mipersona/actualizar', methods=['POST'])
def actualizar():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    dataF = {
        "idPersona": form["id"],
        "nombre": form["nombre"],
        "apellido": form["apellido"],
        "correo": form["correo"],
        "telefono": form["telefono"],
        "dni": form["dni"],
        "clave": form["clave"]
    }

    r = requests.post("http://localhost:8080/myapp/persona/actualizar", data=json.dumps(dataF), headers=headers)
    dat = r.json()
    if r.status_code == 200:
        return redirect(url_for('router.mipersona', id=dataF["idPersona"], lista=dat["data"]))
    else:
        return render_template('modulologin/perfil.html', lista=dat["data"])



    
@router.route('/formularegistro')
def formularegistro():

    return render_template('modulologin/registro.html')

# INICIO PRODUCTO

@router.route('/producto/list')
def list_producto(msg=''):
    if 'token' not in session:
        return redirect(url_for('router.login'))
    r_producto = requests.get("http://localhost:8080/myapp/producto/list")
    data_producto = r_producto.json()

    print(data_producto)

    return render_template('moduloproducto/producto.html', lista_producto=data_producto["data"],usuario=session.get('usuario'), idPersona=session.get('idPersona'))

@router.route('/lote/list')
def list_lote(msg=''):
    if 'token' not in session:
        return redirect(url_for('router.login'))
    
    r_lote = requests.get("http://localhost:8080/myapp/lote/list")
    data_lote = r_lote.json()

    print(data_lote)

    return render_template('modulolote/lote.html', lista_lote=data_lote["data"],usuario=session.get('usuario'), idPersona=session.get('idPersona') )

@router.route('/lote/register')
def view_register_lote():
    if 'token' not in session:
        return redirect(url_for('router.login'))

    r_lote = requests.get("http://localhost:8080/myapp/lote/list")
    data_lote = r_lote.json()
    r_producto = requests.get("http://localhost:8080/myapp/producto/list")
    data_producto = r_producto.json()

    return render_template('modulolote/registro.html', lista_lote=data_lote["data"],lista_producto=data_producto["data"],usuario=session.get('usuario'), idPersona=session.get('idPersona'))



@router.route('/lote/save', methods=['POST'])
def save_lote():
    headers = {'Content-Type': 'application/json'}
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

    r_lote = requests.post("http://localhost:8080/myapp/lote/save", data=json.dumps(data_lote), headers=headers)     # Hacer la petición para guardar la lote
    

    if r_lote.status_code == 200:

        flash("Registro guardado correctamente", category='info')
        return redirect('/lote/list')
    else:
        flash(r_lote.json().get("data", "Error al guardar la lote"), category='error')
        return redirect('/lote/list')

@router.route('/lote/update', methods=['POST'])
def update_lote():
    headers = {'Content-Type': 'application/json'}
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

    r_lote = requests.post("http://localhost:8080/myapp/lote/update", data=json.dumps(data_lote), headers=headers)
    

    if r_lote.status_code == 200:

        flash("Registro guardado correctamente", category='info')
        return redirect('/lote/list')
    else:
        flash(r_lote.json().get("data", "Error al guardar la lote"), category='error')
        return redirect('/lote/list')
    
@router.route('/lote/edit/<id>', methods=['GET'])
def view_edit_lote(id):
    if 'token' not in session:
        return redirect(url_for('router.login'))


    r = requests.get("http://localhost:8080/myapp/lote/listType")
    lista_tipos = r.json()  # Guardamos la respuesta JSON

    r1 = requests.get(f"http://localhost:8080/myapp/lote/get/{id}")     # Obtenemos los datos de la lote por ID


    if r1.status_code == 200:
        data_lote = r1.json()
        lote = data_lote["data"]

        return render_template('modulolote/edit.html', lista=lista_tipos["data"], lote=lote,usuario=session.get('usuario'), idPersona=session.get('idPersona'))

    else:
        flash("Error al obtener la lote", category='error')
        return redirect("/admin/lote/list")



@router.route('/lote/delete/<int:id>', methods=['POST'])
def delete_lote(id):
    if 'token' not in session:
        return redirect(url_for('router.login'))
   
    requests.post(f"http://localhost:8080/myapp/lote/delete/{id}")  
       
    return redirect('/lote/list')    # Redirigimos al usuario a la lista de lotes



@router.route('/producto/register')
def view_register_producto():
    if 'token' not in session:
        return redirect(url_for('router.login'))
    r_producto = requests.get("http://localhost:8080/myapp/producto/list")
    data_producto = r_producto.json()


    return render_template('moduloproducto/registro.html',lista_producto=data_producto["data"],usuario=session.get('usuario'), idPersona=session.get('idPersona'))
    
@router.route('/producto/save', methods=['POST'])
def save_producto():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    data_producto = { 
        "nombre": form["nom"],
        "tipoProducto": form["tipop"],
        "marca": form["marca"],
        "descripcion": form["descripcion"],

    }

    r_producto = requests.post("http://localhost:8080/myapp/producto/save", data=json.dumps(data_producto), headers=headers)     # Hacer la petición para guardar la producto
    

    if r_producto.status_code == 200:

        flash("Registro guardado correctamente", category='info')
        return redirect('/producto/list')
    else:
        flash(r_producto.json().get("data", "Error al guardar el producto"), category='error')
        return redirect('lote/list')
    
@router.route('/producto/delete/<int:id>', methods=['POST'])
def delete_producto(id):
   
    requests.post(f"http://localhost:8080/myapp/producto/delete/{id}")  
       
    return redirect('/producto/list',usuario=session.get('usuario'), idPersona=session.get('idPersona'))    # Redirigimos al usuario a la lista de productos


@router.route('/producto/edit/<id>', methods=['GET'])
def view_edit_producto(id):
    if 'token' not in session:
        return redirect(url_for('router.login'))


    r = requests.get("http://localhost:8080/myapp/producto/listType")
    lista_tipos = r.json()  # Guardamos la respuesta JSON

    r1 = requests.get(f"http://localhost:8080/myapp/producto/get/{id}")     # Obtenemos los datos de la producto por ID


    if r1.status_code == 200:
        data_producto = r1.json()
        producto = data_producto["data"]

        return render_template('moduloproducto/edit.html', lista=lista_tipos["data"], producto=producto)

    else:
        flash("Error al obtener la producto", category='error')
        return redirect("/admin/producto/list")
    

@router.route('/producto/update', methods=['POST'])
def update_producto():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    data_producto = { 
        "id": form["id"],
        "nombre": form["nom"],
        "tipoProducto": form["tipop"],
        "marca": form["marca"],
        "descripcion": form["descripcion"],
    }

    r_producto = requests.post("http://localhost:8080/myapp/producto/update", data=json.dumps(data_producto), headers=headers)
    

    if r_producto.status_code == 200:

        flash("Registro guardado correctamente", category='info')
        return redirect('/producto/list')
    else:
        flash(r_producto.json().get("data", "Error al guardar la producto"), category='error')
        return redirect('/producto/list')

@router.route('/admin/lote/search/<criterio>/<texto>')
def view_buscar_lote(criterio, texto):
    url = 'http://localhost:8080/myapp/lote/list/search/'
    if criterio == 'codigoLote':
        r = requests.get(url+"codigoLote/"+texto)

    data1 = r.json()
    print(f"Response JSON: {data1}")
    if(r.status_code == 200):
        if type(data1["data"]) is dict:
            list=[]
            list.append(data1["data"])
            return render_template ('modulolote/lote.html', lista_lote = list)
            print(f"Lista procesada (dict): {lista}")

        else:
            print(f"Lista procesada (lista): {data1['data']}")
            return render_template ('modulolote/lote.html', lista_lote = data1["data"])

    else:
        return render_template ('modulolote/lote.html', lista_lote = [], message = 'No existe el elemento')
    