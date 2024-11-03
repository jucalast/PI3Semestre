// state.js
import { reactive } from 'vue';

export const globalState = reactive({
    favoriteProductIds: [],
    itemsOnCart: []
});

export function updateFavorites(newFavorites) {
    globalState.favoriteProductIds = newFavorites.map(fav => fav.id);
}

export function updateCartItems(newCartItems){
    globalState.itemsOnCart = newCartItems.map(cartIt => cartIt.id);
}