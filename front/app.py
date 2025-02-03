from flask import Flask
from flask_cors import CORS


def create_app():
    app = Flask(__name__, template_folder="templates", instance_relative_config=False)
    CORS(app)
    app.secret_key = "proyecto_tienda"

    from routes.route import router

    app.register_blueprint(router)

    return app
