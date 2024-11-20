/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{vue,js,ts,jsx,tsx}'],
  theme: {
    extend: {
      colors: {
        primary: '#ff96ff',
        secondary: '#2ecc71',
        accent: '#e74c3c',
        background: '#f4f4f4',
        text: '#4b4b4b',
        border: '#ddd',
        header: '#ffffff',
        inputs: '#d1d1d1',
        btns: '#d1d1d1',
        cardBackground: '#cacaca',
        cardText: '#ffffff',
        elementBackground: 'rgba(59, 59, 59, 0.26)',
        priceBackground: '#49494933',
        headerBackground: '#e4bf5f',
        headerText: '#fff2d2',
        favoriteCard: '#d1d1d1',
        favoriteCardHover: '#e4bf5f',
      },
    },
  },
  plugins: [],
};
