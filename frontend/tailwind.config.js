/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        duolingo: {
          green: '#58CC02',
          'green-hover': '#46A302',
          'green-dark': '#3B7A00',
          blue: '#1CB0F6',
          'blue-hover': '#1899D6',
          red: '#FF4B4B',
          orange: '#FF9600',
          yellow: '#FFC800',
          'gray-bg': '#F7F7F7',
          'gray-card': '#FFFFFF',
          'gray-text': '#777777',
          'gray-border': '#E5E5E5',
          'dark-text': '#1F1F1F',
        }
      },
      fontFamily: {
        display: ['"Nunito"', 'sans-serif'],
      },
      borderRadius: {
        'duolingo': '16px',
      },
      boxShadow: {
        'duolingo': '0 4px 14px rgba(0,0,0,0.15)',
        'duolingo-sm': '0 2px 8px rgba(0,0,0,0.1)',
        'duolingo-btn': '0 4px 0 #46A302',
        'duolingo-btn-white': '0 4px 0 #E5E5E5',
      }
    },
  },
  plugins: [],
}
