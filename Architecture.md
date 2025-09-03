# Architecture

## Overview of Flows

- **Purchase:** Auth + capture in one step
- **Authorize + Capture:** Two-step
- **Cancel:** Before capture
- **Refund:** Full and partial

## API Endpoints

- POST `/api/v1/payments/purchase`
- POST `/api/v1/payments/authorize`
- POST `/api/v1/payments/capture/{transactionId}`
- POST `/api/v1/payments/cancel/{transactionId}`
- POST `/api/v1/payments/refund/{transactionId}?amount={amount}`
- GET `/api/v1/payments/history/{orderId}` (Transaction history for an order)

## DB Schema & Relationships

- `Order`: (id, amount, currency, status, createdAt)
- `Transaction`: (id, transactionId, amount, type, status, transactionDate, order_id)

- **Order** 1 --- N **Transaction**
