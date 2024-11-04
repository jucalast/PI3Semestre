// state.js
import { reactive } from 'vue';

export const globalState = reactive({
    favoriteProductIds: []
});

export function updateFavorites(newFavorites) {
    globalState.favoriteProductIds = newFavorites.map(fav => fav.id);
}