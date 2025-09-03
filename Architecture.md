# Architecture

## Overview of Flows

- **Purchase:** Auth + capture in one step
- **Authorize + Capture:** Two-step
- **Cancel:** Before capture
- **Refund:** Full and partial

## API Endpoints

- POST `/auth/token` (public; returns JWT)
- POST `/payments/purchase`
- POST `/payments/authorize`
- POST `/payments/capture`
- POST `/payments/void`
- POST `/payments/refund`

All `/payments/*` endpoints require `Authorization: Bearer <token>`.

## DB Schema & Relationships

- `orders` (UUID id, external_order_id, customer_email, amount_cents BIGINT, currency, status, description, created_at, updated_at)
- `transactions` (UUID id, order_id FK -> orders(id), gateway_transaction_id, type, amount_cents BIGINT, status, raw_request JSONB, raw_response JSONB, created_at)

- **Order** 1 --- N **Transaction**
