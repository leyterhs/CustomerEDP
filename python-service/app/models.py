from sqlalchemy import Column, Integer, String, Text, DateTime, Numeric, Date, ForeignKey
from sqlalchemy.orm import relationship
from app.database import Base
from datetime import datetime

class Delivery(Base):
    __tablename__ = "deliveries"

    id = Column(Integer, primary_key=True, index=True)
    title = Column(String(150), nullable=False)
    description = Column(Text)
    engagement_id = Column(Integer, ForeignKey("engagements.id"))
    assigned_to = Column(Integer, ForeignKey("members.id"))
    priority = Column(String(20), default="MEDIUM")
    status = Column(String(20), default="PENDING")
    due_date = Column(Date)
    created_at = Column(DateTime, default=datetime.utcnow)

class Engagement(Base):
    __tablename__ = "engagements"

    id = Column(Integer, primary_key=True, index=True)
    title = Column(String(150), nullable=False)
    description = Column(Text)
    client_id = Column(Integer, ForeignKey("clients.id"))
    status = Column(String(20), default="ACTIVE")
    budget = Column(Numeric(10, 2))
    deadline = Column(Date)
    created_by = Column(Integer, ForeignKey("members.id"))
    created_at = Column(DateTime, default=datetime.utcnow)

    deliveries = relationship("Delivery", backref="engagement")