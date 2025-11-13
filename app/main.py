from flask import Flask, jsonify

app = Flask(__name__)


@app.route("/")
def index() -> str:
    return "Hello from ulala demo!"


@app.route("/health")
def health() -> tuple:
    payload = {"status": "ok"}
    return jsonify(payload), 200


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)
