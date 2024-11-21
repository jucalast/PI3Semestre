<template>
  <div class="favorites-section">
    <h3>Veja quais cafés estão bombando!</h3>
    <ul>
      <li v-for="product in favoriteProducts" :key="product.id">
       
        <img :src="product.imagens[0]" :alt="product.nome" class="product-image" />
        <div class="NAMEANDIMAGE">
        <span>{{ product.nome }}</span>

        <div class="favorite-info">
          <font-awesome-icon icon="star" class="favorite-icon" />
          <span>{{ product.favoriteCount }}</span>
        </div>
    </div>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

export default {
  name: 'FavoriteCountComponent',
  components: {
    FontAwesomeIcon,
  },
  data() {
    return {
      favoriteProducts: [],
    };
  },
  methods: {
    async fetchFavoriteCounts() {
      try {
        const response = await axios.get("http://localhost:8080/api/favorites/count-by-product");
        const favoriteCounts = response.data;

        const productIds = Object.keys(favoriteCounts);
        const productResponses = await Promise.all(
          productIds.map(id => axios.get(`http://localhost:8080/api/produtos/${id}`))
        );

        this.favoriteProducts = productResponses.map((response, index) => ({
          ...response.data,
          favoriteCount: favoriteCounts[productIds[index]],
        }));
      } catch (error) {
        console.error("Erro ao buscar contagem de favoritos:", error);
      }
    },
  },
  mounted() {
    this.fetchFavoriteCounts();
  },
};
</script>

<style scoped>

.NAMEANDIMAGE {
    display: flex
;
    flex-direction: column;
    align-items: flex-start;
}
.favorites-section {
  margin-top: 0 !important;
  padding: 2rem;
  background: #00000017!important;
  border-radius: 2rem !important;
}

h3 {
  width: 80%;
  font-size: 3rem;
  line-height: 1 !important;
  padding-bottom: 2rem;
  color:#ffffff;
}

.product-image {
  width: 4em;
  height: 10rem;
  object-fit: cover;
  margin-right: 1rem;
  border-radius:1rem;
}

li {
    display: flex
;
    flex-direction: row;
    align-items: center;
    justify-content: flex-start;
    font-size: 1.5rem;
    padding: 1.5rem;
border-top:#ffffff51 solid 1px;
border-bottom:#ffffff51 solid 1px;
    color:#ffffff;
    
    padding-left:0%
}

.favorite-info {
  display: flex;
  align-items: center;
  margin-left: 1rem;
}

.favorite-icon {
  color: #ffc107; /* Cor amarela para a estrela */
  margin-right: 0.5rem;
}
</style>