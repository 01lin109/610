# Design

## Theme

Taobao-inspired commerce admin — warm, energetic, trustworthy. The interface should feel like a well-organized marketplace control room: purposeful warmth in the brand colors, clean white surfaces for data density, and clear hierarchy through color and spacing.

## Color Palette

All colors in OKLCH.

```css
/* Brand & Strategy */
/* Inspired by Taobao's iconic orange (#FF4400 → oklch(0.65 0.22 30)) */

/* Light theme (default — operators work in office lighting) */

--color-bg:        oklch(1.000 0.000 0);       /* pure white — data surface */
--color-surface:   oklch(0.965 0.003 40);      /* warm off-white for cards, panels */
--color-sidebar:   oklch(0.250 0.010 30);      /* deep warm-dark for navigation */
--color-sidebar-hover: oklch(0.320 0.015 30);  /* slightly lighter on hover */

--color-primary:      oklch(0.650 0.220 34);    /* Taobao orange — primary actions, links */
--color-primary-hover: oklch(0.580 0.230 34);   /* deeper orange on hover */
--color-primary-light: oklch(0.780 0.150 34);   /* light orange for badges, highlights */
--color-primary-bg:    oklch(0.920 0.060 34);   /* tinted background for selected items */

--color-accent:       oklch(0.550 0.200 25);    /* Taobao red — promotions, urgency, danger */
--color-accent-hover: oklch(0.480 0.210 25);    /* deeper red on hover */
--color-accent-light: oklch(0.700 0.140 25);    /* light red for badges, tags */

--color-success:      oklch(0.650 0.180 142);   /* fresh green — completed, active */
--color-warning:      oklch(0.750 0.180 75);    /* amber-gold — pending, attention */
--color-info:         oklch(0.600 0.050 220);   /* blue-gray — neutral info */

--color-ink:          oklch(0.200 0.008 40);    /* near-black text — body */
--color-ink-secondary: oklch(0.450 0.015 40);   /* secondary text, labels */
--color-ink-muted:    oklch(0.600 0.010 40);    /* muted text, placeholders */
--color-ink-inverse:  oklch(0.950 0.005 40);    /* white text on dark fills */

--color-border:       oklch(0.880 0.005 40);    /* light warm-gray borders */
--color-border-light: oklch(0.930 0.003 40);    /* subtle divider lines */
```

## Color Roles in Context

| Role | Usage | Element |
|------|-------|---------|
| Primary (orange) | Primary buttons, active nav item, key links, primary stat values | `.el-button--primary`, active menu item |
| Accent (red) | Danger actions, urgent status, discount badges, delete | `.el-button--danger`, `.el-tag--danger` |
| Success (green) | Completed orders, active status, stock-ok indicators | `.el-tag--success`, status dots |
| Warning (amber) | Pending items, inventory alerts, mid-status | `.el-tag--warning` |
| Info (blue-gray) | Neutral status, secondary indicators | `.el-tag--info` |

## Typography

- **Family**: `-apple-system, BlinkMacSystemFont, 'Segoe UI', 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', sans-serif` (system font stack — no external fonts needed for an internal tool)
- **Scale**: Fixed rem scale (product UI, not fluid)
  - Body: 14px (0.875rem)
  - Small/text: 12px (0.75rem)
  - Labels/buttons: 14px (0.875rem)
  - H1: 24px (1.5rem)
  - H2: 20px (1.25rem)
  - H3: 16px (1rem)
- **Line length**: 65-75ch for prose; tables and compact UI can run denser

## Spacing Scale

Based on 4px unit:
- 4px / 8px / 12px / 16px / 20px / 24px / 32px / 40px / 48px / 64px

## Border Radius

- Buttons, inputs, cards: 6px
- Tags, badges, small elements: 4px
- Modals, dialogs: 8px
- Sidebar: 0 (full height)

## Shadow

```css
--shadow-sm: 0 1px 2px rgba(0,0,0,0.05);
--shadow-md: 0 2px 8px rgba(0,0,0,0.08);
--shadow-lg: 0 4px 16px rgba(0,0,0,0.12);
```

## Motion

- Duration: 200-250ms for most transitions
- Easing: ease-out for enter, ease-in-out for state changes
- Reduced motion: `@media (prefers-reduced-motion: reduce)` → 0ms transitions
- No decorative entrance animations (product UI, users are in flow)

## Component Design

### Navigation (Sidebar)
- Background: `--color-sidebar` (deep warm-dark)
- Active item: orange left border + light orange tint
- Hover item: slightly lighter background
- Collapsed: icon-only with tooltips
- Logo area: brand name in white, subtle bottom border

### Header (Top bar)
- Background: pure white
- Bottom border: `--color-border`
- Breadcrumb: secondary text color
- User avatar + dropdown: right-aligned

### Buttons
- Primary: `--color-primary` bg, white text, hover darkens 10%
- Danger: `--color-accent` bg, white text
- Default: white bg, `--color-border` stroke, `--color-ink` text
- Small: 8px vertical padding, 12px horizontal
- Large: 14px vertical padding, 20px horizontal

### Tables
- Striped rows: alternating `--color-bg` and `--color-surface`
- Header: `--color-surface` background, bold `--color-ink` text
- Border: `--color-border` light
- Row hover: `--color-primary-bg` at 30% opacity
- Pagination: consistent at bottom, background style

### Form Elements
- Input focus: `--color-primary` border ring
- Labels: `--color-ink-secondary`, right-aligned on desktop
- Error state: `--color-accent` border + message
- Disabled: muted background, reduced opacity

### Status Tags
- Success: `--color-success` fill or light variant
- Warning: `--color-warning` fill or light variant
- Danger: `--color-accent` fill or light variant
- Info/default: `--color-info` fill or light variant

### Cards
- Background: `--color-bg`
- Border: `--color-border-light`
- Header: optional, slightly larger text, flex layout for actions
- Shadow: `--shadow-sm` resting, `--shadow-md` on hover (for clickable cards)

## Responsive Behavior

- Sidebar collapses to icon-only at ≤1200px viewport
- Tables: horizontal scroll on narrow viewports, no font-size reduction
- Search forms: inline on desktop, stacked on mobile
- Cards: 4-column stat row → 2-column → 1-column on narrow screens
