import json

from main import app

def test_health_endpoint_returns_ok():
    client = app.test_client()
    response = client.get("/health")
    assert response.status_code == 200
    body = json.loads(response.data)
    assert body["status"] == "ok"
