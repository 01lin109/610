# Product

## Register

product

## Users

E-commerce platform administrators and operations staff — internal team members managing a marketplace backend. They are task-oriented, working in a browser at a desk, often multitasking across orders, products, members, promotions, and financial records. Speed and clarity are paramount; every second saved in a repeated workflow compounds across the team.

## Product Purpose

A comprehensive backend management system for an online marketplace. It enables admins to manage products (catalog, categories, inventory), orders (fulfillment, status, refunds), members, promotions (coupons, activities), content (articles, notices), operations (banners, ads), financial transactions, permissions (users, roles, menus, departments), and system settings. Success means operators can complete any management task without leaving the tool or waiting for page loads.

## Brand Personality

Trustworthy, energetic, commerce-native. The interface should feel like a well-run shop floor — organized, purposeful, with just enough warmth to make daily work pleasant. Think: the focused hum of a busy trading floor, not a sterile corporate back office.

## Anti-references

- Overly corporate / cold / "enterprise" UIs (SAP, legacy ERP systems) — no dense gray forms with tiny labels
- Purple gradients or "creative agency" aesthetics on the login page — the current gradient is mismatched for a commerce tool
- Dark mode as default — operators work under normal office lighting
- Decoratively colored sidebars without functional hierarchy
- Flat, colorless tables that make data hard to scan

## Design Principles

- **Commerce-native warmth**: The tool serves a marketplace; the palette should reflect energy and transaction (warm tones, clear action hierarchy), not cold utility.
- **Scan-first data density**: Tables and lists are the primary workspace. Every visual decision serves faster scan-and-act — proper row spacing, clear status indicators, consistent action placement.
- **Consistency over surprise**: One component vocabulary across every screen. Same button hierarchy, same form patterns, same table behavior. Operators should never pause to re-learn.
- **Action visibility**: Primary actions (create, edit, save, approve) must be immediately visible and use consistent color roles. Don't bury operations in menus or flat links.
- **State clarity**: Every interactive element communicates its state (default, hover, active, disabled, loading, error) without ambiguity.

## Accessibility & Inclusion

- WCAG 2.1 AA minimum (body text ≥4.5:1 contrast, large text ≥3:1)
- Focus indicators on all interactive elements
- Semantic HTML structure via Element Plus components (already aria-compliant)
- Reduced motion respected via `prefers-reduced-motion`
