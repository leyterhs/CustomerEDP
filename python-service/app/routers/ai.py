from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from datetime import date
from app import models, schemas
from app.database import get_db

router = APIRouter(prefix="/api/ai", tags=["AI"])

def calculate_suggestion(delivery, engagement):
    today = date.today()
    days_until_due = (delivery.due_date - today).days if delivery.due_date else 999

    if days_until_due <= 1:
        suggested = "HIGH"
        reason = "Η προθεσμία είναι αύριο ή σήμερα – άμεση προτεραιότητα."
    elif days_until_due <= 3:
        suggested = "HIGH"
        reason = "Η προθεσμία είναι σε 3 ημέρες – υψηλή προτεραιότητα."
    elif days_until_due <= 7:
        suggested = "MEDIUM"
        reason = "Η προθεσμία είναι σε μία εβδομάδα – μεσαία προτεραιότητα."
    else:
        suggested = "LOW"
        reason = "Υπάρχει αρκετός χρόνος – χαμηλή προτεραιότητα."

    return suggested, reason

@router.get("/suggest/{delivery_id}", response_model=schemas.AISuggestionResponse)
def suggest_priority(delivery_id: int, db: Session = Depends(get_db)):
    delivery = db.query(models.Delivery).filter(models.Delivery.id == delivery_id).first()
    if not delivery:
        raise HTTPException(status_code=404, detail="Delivery not found")

    engagement = db.query(models.Engagement).filter(models.Engagement.id == delivery.engagement_id).first()
    if not engagement:
        raise HTTPException(status_code=404, detail="Engagement not found")

    suggested, reason = calculate_suggestion(delivery, engagement)

    return schemas.AISuggestionResponse(
        delivery_id=delivery.id,
        current_priority=delivery.priority,
        suggested_priority=suggested,
        reason=reason
    )