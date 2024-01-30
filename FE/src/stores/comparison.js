import { defineStore } from 'pinia';

export const useComparisonStore = defineStore('comparison', {
  state: () => ({
    basket: [],
  }),
  actions: {
    addToBasket(drink) {
      if (this.basket.length < 3) {
        this.basket.push(drink);
      }
    },
    removeFromBasket(index) {
      this.basket.splice(index, 1);
    },
    saveBasketToSession() {
      sessionStorage.setItem('comparisonBasket', JSON.stringify(this.basket));
    },
    loadBasketFromSession() {
      const storedBasket = sessionStorage.getItem('comparisonBasket');
      if (storedBasket) {
        this.basket = JSON.parse(storedBasket);
      }
    }
  }
});
