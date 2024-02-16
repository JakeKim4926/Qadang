import { defineStore } from 'pinia';

export const useComparisonStore = defineStore('comparison', {
  state: () => ({
    basket: [],
  }),
  actions: {
    addToBasket(drink) {      
      const isAlreadyInBasket = this.basket.some(item => item.drinkId === drink.drinkId);
      if (!isAlreadyInBasket && this.basket.length < 3) {
        this.basket.push(drink);
        this.saveBasketToSession();
      }
    },
    removeFromBasket(index) {
      this.basket.splice(index, 1);
      this.saveBasketToSession();
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
