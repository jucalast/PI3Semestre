<template>
  <DefaultLayout @search="updateSearchQuery">
    <div class="product-page">
      <div class="sessiontwo">
        <!-- Adicionando o Acordeon para selecionar os filtros -->
        <AtributosProduto
          @produtos-filtrados-atualizados="updateFilteredProducts"
          @update-selected-radios="updateSelectedRadios"
        />

        <div class="product-list no-margin">
          <!-- Componente para exibir a lista de produtos filtrados -->
          <ProductCard
            :produtos="produtos"
            :searchQuery="searchQuery"
            :isLoading="isLoading"
          />
        </div>
      </div>
    </div>
  </DefaultLayout>
</template>

<script>
import DefaultLayout from "@/layouts/DefaultLayout.vue";
import ProductCard from "@/components/ProductCard.vue";
import AtributosProduto from "@/components/AtributosProduto.vue";
import axios from "axios";

export default {
  components: {
    DefaultLayout,
    ProductCard,
    AtributosProduto,
    
  },
  data() {
    return {
      produtos: [],         // Lista de produtos
      isLoading: true,      // Indica se a lista de produtos está carregando
      searchQuery: "",      // Termo de busca
    };
  },
  methods: {
    async fetchProdutos() {
      try {
        const response = await axios.get("http://localhost:8080/api/produtos");
        this.produtos = response.data;
      } catch (error) {
        console.error("Erro ao buscar produtos:", error);
      } finally {
        this.isLoading = false;
      }
    },
    
    updateSearchQuery(query) {
      this.searchQuery = query;
    },
    
    updateFilteredProducts(produtos) {
      this.produtos = produtos; // Atualiza a lista de produtos filtrados
    },
  },
  mounted() {
    this.fetchProdutos();
  },
};
</script>


<style scoped>
.product-list {
  display: flex;
  flex-wrap: wrap;
 
justify-content: flex-start;
  padding-top: 2rem;
  width: 80%;
}



.no-margin {
  margin: 0;
  padding: 0;
}


.sessiontwo {
  padding: 2rem; /* Adiciona espaço ao redor do conteúdo */
  display: flex !important;
}

.accordion {
  margin-bottom: 2rem; /* Espaço inferior entre o accordion e a lista de produtos */
}


</style>
