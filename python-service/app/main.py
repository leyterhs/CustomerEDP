from fastapi import FastAPI
from app.routers import ai
from app.database import engine, Base

# Δημιουργία πινάκων (αν δεν υπάρχουν)
Base.metadata.create_all(bind=engine)

app = FastAPI(title="CustomerEDP AI Service", version="1.0.0")

app.include_router(ai.router)

@app.get("/health")
def health_check():
    return {"status": "ok", "service": "python-ai"}