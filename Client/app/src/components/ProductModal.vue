<template>
  <div v-if="isVisible && showModal" class="modal-overlay" @click.self="close">
    <div class="modal-content">
      <!-- Carrossel de Comentários -->
      <div class="comments-carousel" v-if="comments.length > 0">
        <p>
          <img :src="comments[currentCommentIndex].userAvatar" alt="User Avatar" class="user-avatar" />
          <span class="user-name">{{ comments[currentCommentIndex].userName + ":" }}</span>
          {{ comments[currentCommentIndex].descricao }}
        </p>
      </div>

      <div class="infoprod">
        <div class="product-rating">
          <div class="rating-stars">
            <span
              class="fa fa-star"
              v-for="star in 5"
              :key="'rating-' + star"
              :class="getStarClass(star)"
            ></span>
          </div>
        </div>

        <p class="nome">
          <strong> {{ product.nome }} </strong>
        </p>
        <p class="preco">
          <strong></strong> R$ {{ product.preco?.toFixed(2) }}
        </p>
        <!-- Adicionar os três botões abaixo da quantidade em estoque -->
        <div class="action-buttons">
          <div class="sacolaandfav">
            <button class="add-to-cart" @click="addToCart(product)">
              Sacola
            </button>
            <!-- No modal, dentro do template -->
            <button
              class="favorite-button"
              @click.stop="handleFavoriteClick(product)"
            >
              <font-awesome-icon
                icon="star"
                :class="{
                  favoritocard: true,
                  'is-favorite': favoriteProductIds.includes(product.id),
                }"
              />
            </button>
          </div>
          <button class="buy-now" @click="buyNow(product)">
            Comprar Agora
          </button>
        </div>
        <p class="qtdestoque">
          <strong></strong>{{ product.quantidade_estoque }} disponíveis
        </p>
      </div>
      <div class="imagemproduto">
        <ImageCarousel :images="product.imagens" />
      </div>

      <!-- Acordions para informações do produto -->
      <div class="accordion">
        <div class="accordion-item">
          <div class="accordion-header" @click="toggleAccordion('descricao')">
            <h4>Descrição</h4>
            <span>{{ isOpen.descricao ? "-" : "+" }}</span>
          </div>
          <div v-if="isOpen.descricao" class="accordion-content">
            <p>{{ product.descricao || "Descrição não disponível" }}</p>
          </div>
        </div>

        <!-- Accordion para Características (apenas se for café especial) -->
        <div v-if="product.cafeEspecial" class="accordion-item">
          <div
            class="accordion-header"
            @click="toggleAccordion('caracteristicas')"
          >
            <h4>Características</h4>
            <span>{{ isOpen.caracteristicas ? "-" : "+" }}</span>
          </div>
          <div v-if="isOpen.caracteristicas" class="accordion-content">
            <p>
              <strong>Origem:</strong>
              {{
                product.cafeEspecial[0]?.origem || "Informação não disponível"
              }}
            </p>
            <p>
              <strong>Variedade:</strong>
              {{
                product.cafeEspecial[0]?.variedade ||
                "Informação não disponível"
              }}
            </p>
            <p>
              <strong>Torra:</strong>
              {{
                product.cafeEspecial[0]?.torra || "Informação não disponível"
              }}
            </p>
            <p>
              <strong>Notas Sensoriais:</strong>
              {{
                product.cafeEspecial[0]?.notasSensoriais ||
                "Informação não disponível"
              }}
            </p>
            <p>
              <strong>Data de Validade:</strong>
              {{
                product.cafeEspecial[0]?.dataValidade ||
                "Informação não disponível"
              }}
            </p>
          </div>
        </div>

        <!-- Accordion para Método de Preparo (apenas se houver método de preparo) -->
        <div v-if="product.metodoPreparo" class="accordion-item">
          <div
            class="accordion-header"
            @click="toggleAccordion('metodoPreparo')"
          >
            <h4>Caracteristicas</h4>
            <span>{{ isOpen.metodoPreparo ? "-" : "+" }}</span>
          </div>
          <div v-if="isOpen.metodoPreparo" class="accordion-content">
            <p>
              <strong>Tipo de Preparo:</strong>
              {{
                product.metodoPreparo.tipoPreparo || "Informação não disponível"
              }}
            </p>
            <p>
              <strong>Material:</strong>
              {{
                product.metodoPreparo.material || "Informação não disponível"
              }}
            </p>
            <p>
              <strong>Acessórios:</strong>
              {{
                product.metodoPreparo.acessorios || "Informação não disponível"
              }}
            </p>
            <p>
              <strong>Complexidade:</strong>
              {{
                product.metodoPreparo.complexidade ||
                "Informação não disponível"
              }}
            </p>
            <p>
              <strong>Marca:</strong>
              {{ product.metodoPreparo.marca || "Informação não disponível" }}
            </p>
          </div>
        </div>

        <!-- Accordion para Outros Detalhes -->
        <div v-if="product.cafeEspecial || product.metodoPreparo" class="accordion-item">
          <div
            class="accordion-header"
            @click="toggleAccordion('outrosDetalhes')"
          >
            <h4>Outros Detalhes</h4>
            <span>{{ isOpen.outrosDetalhes ? "-" : "+" }}</span>
          </div>
          <div v-if="isOpen.outrosDetalhes" class="accordion-content">
            <p v-if="product.cafeEspecial[0]?.recomendacoesPreparo">
              {{ product.cafeEspecial[0]?.recomendacoesPreparo }}
            </p>
            <p v-else>Não há recomendações de preparo disponíveis.</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axiosInstance from "@/utils/axiosInstance";
import { globalState } from "@/state";
import { computed, ref, onMounted, watch } from "vue";
import ImageCarousel from "@/components/ImageCarousel.vue";

export default {
  props: {
    product: {
      type: Object,
      required: true,
    },
    isVisible: {
      type: Boolean,
      required: true,
    },
  },
  data() {
    return {
      comments: [],
      currentCommentIndex: 0,
      commentInterval: null,
      isOpen: {
        descricao: false,
        caracteristicas: false,
        metodoPreparo: false,
        outrosDetalhes: false,
      },
      averageRating: 0,
      showModal: true,
    };
  },
  setup() {
    const favoriteProductIds = computed(() => globalState.favoriteProductIds);
    return {
      favoriteProductIds,
    };
  },
  components: {
    ImageCarousel,
  },
  watch: {
    isVisible(newValue) {
      console.log("Visibility changed:", newValue);
      if (newValue) {
        this.fetchComments();
        this.fetchAverageRating();
        this.disableScroll();
        this.showModal = true;
      } else {
        clearInterval(this.commentInterval);
        this.enableScroll();
      }
    },
  },
  methods: {
    async fetchAverageRating() {
      try {
        const response = await axiosInstance.get(
          `http://localhost:8080/avaliacoes/media/${this.product.id}`
        );
        this.averageRating = response.data;
        console.log("Fetched average rating:", this.averageRating);
      } catch (error) {
        console.error("Erro ao buscar a média de avaliações:", error);
        this.averageRating = 0;
        if (error.response && error.response.status === 404) {
          alert("Produto não encontrado para buscar a média de avaliações.");
        } else {
          alert("Erro ao buscar a média de avaliações. Por favor, tente novamente mais tarde.");
        }
      }
    },
    getStarClass(star) {
      const floorRating = Math.floor(this.averageRating);
      const remainder = this.averageRating - floorRating;

      if (star <= floorRating) {
        return "checked"; // estrela completamente preenchida
      } else if (star === floorRating + 1 && remainder >= 0.5) {
        return "half"; // meia estrela para 0.5 ou mais
      }
      return ""; // estrela não preenchida
    },
    async fetchComments() {
      try {
        const response = await axiosInstance.get(
          `http://localhost:8080/avaliacoes/produto/${this.product.id}`
        );
        this.comments = response.data;
        console.log("Comments fetched:", this.comments);
        if (this.comments.length > 0) {
          this.currentCommentIndex = 0; // Reset to first comment
          this.commentInterval = setInterval(() => {
            this.currentCommentIndex =
              (this.currentCommentIndex + 1) % this.comments.length;
            console.log(
              "Updating current comment index:",
              this.currentCommentIndex
            );
          }, 4000); // Change comment every 3 seconds
        }
      } catch (error) {
        console.error("Erro ao buscar comentários:", error);
        this.comments = [];
        alert("Erro ao buscar comentários. Por favor, tente novamente mais tarde.");
      }
    },
    close() {
      console.log("Modal closed.");
      this.$emit("close");
      this.showModal = false;
    },
    toggleAccordion(section) {
      console.log(`Toggling accordion for section: ${section}`);
      this.isOpen[section] = !this.isOpen[section];
    },
    async addToCart(product) {
      try {
        const response = await axiosInstance.post(`/api/carrinho/${product.id}`);
        if (response.status === 200) {
          alert(`Produto ${product.nome} adicionado ao carrinho.`);
        } else {
          alert('Erro ao adicionar produto ao carrinho.');
        }
      } catch (error) {
        console.error('Erro ao adicionar produto ao carrinho:', error);
        alert('Erro ao adicionar produto ao carrinho.');
      }
    },
    buyNow(product) {
      this.$router.push({ name: 'Checkout', query: { ids: product.id } });
    },
    async handleFavoriteClick(produto) {
      try {
        const params = new URLSearchParams();
        params.append("productId", produto.id);
        const response = await axiosInstance.post(
          `/api/favorites/add?${params.toString()}`
        );
        console.log("Favorite toggle response:", response);
        if (response.status === 200) {
          await this.fetchFavorites(); // Isto re-fetch os favoritos atualizados do servidor
        } else {
          console.error("Falha ao alterar o estado do favorito");
        }
      } catch (error) {
        console.error("Erro ao alterar o estado do favorito:", error);
      }
    },
    async fetchFavorites() {
      try {
        const response = await axiosInstance.get(`/api/favorites/list`);
        console.log("Fetched favorites:", response.data);
        if (Array.isArray(response.data)) {
          globalState.favoriteProductIds = response.data.map(
            (fav) => fav.productId
          );
        } else {
          globalState.favoriteProductIds = []; // Limpa a lista se a resposta não for um array
        }
      } catch (error) {
        console.error("Erro ao buscar favoritos:", error.message);
        globalState.favoriteProductIds = []; // Limpa a lista em caso de erro na requisição
      }
    },
    disableScroll() {
      console.log("Scrolling disabled.");
      document.body.classList.add("no-scroll");
    },
    enableScroll() {
      console.log("Scrolling enabled.");
      document.body.classList.remove("no-scroll");
    },
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap");

.product-page .cards {
  margin-top: 5rem;
  width: 100%;
}
/* Adicione estilos para o accordion */
.accordion {
  margin-top: 1rem;
  border-radius: 5px;
  width: 30%;
  height: 80%;
  max-height: 80%;
  overflow-y: auto;
  overflow-x: hidden;
  display: flex;
  justify-content: center;
  flex-direction: column;
}

.accordion p font {
  font-size: 1.5rem !important;
  color: var(--text-color) !important;
}

.accordion-item {
  border-bottom: 1px solid #ccc;
  width: 100%;
}

.accordion-header {
  display: flex;
  justify-content: space-between;
  padding: 1rem;
  cursor: pointer;

  flex-direction: row;
  align-content: flex-start;
  align-items: center;
}

.accordion-content {
  display: flex;
  padding: 1rem;
  background-color: #fff;
  width: 100%;
  flex-direction: column;
}
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.7);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 300;
  backdrop-filter: blur(5px);
}

.sacolaandfav {
  display: flex;
}

.no-scroll {
  overflow: hidden; /* Impede o scroll */
}

.nome {
  font-size: 3rem;
  line-height: 1; /* Diminui o espaçamento entre as linhas */
  text-transform: uppercase;
}

.preco {
  font-size: 2rem;
  font-weight: bold;
  padding-top: 0;
}

.infoprod {
  display: flex;
  width: 30%;
  flex-direction: column;
  align-content: flex-start;
  align-items: flex-start;
}

.infoprod p {
  backdrop-filter: blur(0);
}

.imagemproduto {
  width: 25%;
  height: 100%; /* Alterado para ocupar 100% da altura */
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 2rem;
  width: 80%;
  height: 80%;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  align-content: center;
  justify-content: space-evenly;
  align-items: center;
  font-family: "Poppins", sans-serif;
  font-size: 16px; /* Definir padrão de font-size */
}

.modalimage {
  width: 100% !important;
  position: relative;
}

.modal-content p {
  background: transparent !important;
  width: fit-content;

  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
}

h4 {
  font-size: 2rem;
  font-weight: lighter;
}

span {
  font-size: 2rem;
}

.modal-content .action-buttons {
  display: flex;
  gap: 0.5rem;
  margin-top: 1.5rem;
  flex-direction: column;
}

.add-to-cart {
  background-color: #313131;
  color: #fff;
  border: none;
  padding: 0.8rem 1.2rem;
  border-radius: 1.5rem;
  font-size: 1.5rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
  height: 4rem;
}
.add-to-cart:hover {
  background-color: #1e1e1e;
}

.buy-now {
  background-color: #5f75e4;
  color: #fff;
  border: none;
  padding: 0.8rem 1.2rem;
  border-radius: 1.5rem;
  font-size: 1.5rem;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s ease;
  height: 4rem;
}
.buy-now:hover {
  background-color: #4b5fb4;
}

.accordion .action-buttons {
  display: flex;
  align-items: center;
  justify-content: space-around;
  border-radius: 2rem;
  height: 3rem;
  width: 13%;
  padding-left: 0.5rem !important;
  padding-right: 0.5rem !important;
  flex-direction: row;
}

.action-button {
  justify-content: center;
  background: transparent;
  border: none;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0.1rem;
  border-radius: 50%;
}

.action-button img {
  width: 1.5rem;
  height: 1.5rem;
  filter: invert(1);
}

.favorite-button {
  justify-content: center;
  background-color: rgba(94, 94, 94, 0.281);
  backdrop-filter: blur(5px);
  border: none;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0.1rem;
  border-radius: 50%;
  padding: 0.5rem;
  width: 4rem;
}

.favoritocard {
  color: var(--favoritocard-color);
  font-size: 1.5rem; /* Tamanho do ícone */
  transition: transform 0.3s ease, color 0.3s ease;
  width: 2rem !important;
  height: 2rem !important;
}
.favoritocard:hover {
  color: var(--favoritocard-hover-color);
  transform: scale(1.2); /* Aumenta o ícone em 20% */
}

.favoritocard.is-favorite {
  color: var(
    --favoritocard-hover-color
  ); /* Altera a cor para a cor de favorito ativo */
}

.favorite-button {
  background: none;
  border: none;
  cursor: pointer;
}

.is-favorite {
  color: var(--favoritocard-hover-color);
  transform: scale(1.2); /* Aumenta o ícone em 20% */
}

.comments-carousel {
  position: absolute;
  top: 15%; /* Posição ajustada */
  left: 60%; /* Centralizar horizontalmente */
  transform: translateX(-50%); /* Ajuste fino para centralização perfeita */
  width: auto;
  background-color: rgb(32, 32, 32);
  padding: 1rem;
  border-radius: 25px;
  z-index: 10; /* Garantir que apareça acima da imagem */
  text-align: center;
  font-size: 1.5rem;
  color:#ffffff;
}
p {
  display:flex !important;
  flex-direction:row !important;
  align-items: center !important;
  gap:1rem;
}

.fa-star {
  color: #ddd; /* Cor das estrelas não preenchidas */
  display: inline-block; /* Garante alinhamento correto */
}

.checked {
  color: #ffc107; /* Estrelas completamente preenchidas */
}

.half {
  position: relative;
  display: inline-block;
  color: #ffc107; /* Cor das estrelas preenchidas */
}

.half::before {
  content: "\f005"; /* Código Unicode para a estrela completa (FontAwesome) */
  display: block;
  mask-image: linear-gradient(to right, black 50%, transparent 50%);
}

.user-name {
  color: #a7a7a7; /* Cor de exemplo, ajuste conforme necessário */
  font-size: 1.5rem;
}

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
  margin-right: 10px;
}
</style>