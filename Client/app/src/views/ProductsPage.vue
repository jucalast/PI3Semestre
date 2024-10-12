<template>
  <DefaultLayout @search="updateSearchQuery">
    <div class="product-page">
      <div class="sessiontwo">
        <!-- Adicionando o Acordeon -->
        <AtributosProduto />

        <div class="product-list no-margin">
          <ProductCard :produtos="filteredProducts" :searchQuery="searchQuery" :isLoading="isLoading" />
        </div>
      </div>
    </div>
  </DefaultLayout>
</template>


<script>
import DefaultLayout from '@/layouts/DefaultLayout.vue';
import ProductCard from '@/components/ProductCard.vue';
import AtributosProduto  from '@/components/AtributosProduto.vue'; // Importação do acordeon
import axios from 'axios';

export default {
  components: {
    DefaultLayout,
    ProductCard,
    AtributosProduto , // Registro do componente
  },
  data() {
    return {
      produtos: [],
      atributos: [], // Nova propriedade para os atributos do accordion
      isLoading: true,
      searchQuery: "",
    };
  },
  computed: {
    filteredProducts() {
      if (!this.searchQuery) {
        return this.produtos;
      }
      return this.produtos.filter(produto => 
        produto.nome.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    }
  },
  methods: {
    async fetchProdutos() {
      try {
        const response = await axios.get("http://localhost:8080/api/produtos");
        this.produtos = response.data;
        this.extractAtributos(); // Extrai os atributos dos produtos para o accordion
      } catch (error) {
        console.error("Erro ao buscar produtos:", error);
      } finally {
        this.isLoading = false;
      }
    },
    extractAtributos() {
      // Supondo que você quer pegar alguns atributos dos produtos
      this.atributos = this.produtos.map(produto => ({
        avaliacao: produto.avaliacao,
        notasSensoriais: produto.notasSensoriais,
        origem: produto.origem,
        recomendacoesPreparo: produto.recomendacoesPreparo,
        torra: produto.torra,
        torrefacao: produto.torrefacao,
        variedade: produto.variedade,
        complexidade: produto.complexidade,
        marca: produto.marca,
        material: produto.material,
        tipoPreparo: produto.tipoPreparo,
      }));
    },
    updateSearchQuery(query) {
      this.searchQuery = query;
    }
  },
  mounted() {
    this.fetchProdutos(); // Chama a função uma vez ao montar o componente
  },
};
</script>

<style scoped>
.product-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  padding-top: 2rem;
  width: 80%;
}

.no-margin {
  margin: 0;
  padding: 0;
}

.banner {
  position: absolute;
  width: 95%;
  height: 15rem;
  padding: 2rem;
}

.sessionone {
  background: #c2cdff;

}

.sessiontwo {
  padding: 2rem; /* Adiciona espaço ao redor do conteúdo */
  display: flex !important;
}

.accordion {
  margin-bottom: 2rem; /* Espaço inferior entre o accordion e a lista de produtos */
}
</style>
