from pydantic import BaseModel
from datetime import date, datetime
from typing import Optional

class DeliveryBase(BaseModel):
    id: int
    title: str
    description: Optional[str] = None
    priority: str = "MEDIUM"
    status: str = "PENDING"
    due_date: Optional[date] = None

class DeliveryWithEngagement(DeliveryBase):
    engagement_id: int
    engagement_title: str
    engagement_deadline: Optional[date] = None

class AISuggestionResponse(BaseModel):
    delivery_id: int
    current_priority: str
    suggested_priority: str
    reason: str