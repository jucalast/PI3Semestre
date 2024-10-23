<template>
  <div v-if="isVisible" class="modal-overlay" @click.self="close">
    <div class="modal-content">
      <header>
        <!-- Accordion para Descrição -->
        <div class="action-buttons">
          <button
            class="action-button favorite-button"
            @click="handleFavoriteClick"
          >
            <img src="@/assets/estrela.png" alt="Favorites" />
          </button>
          <button class="action-button cart-button" @click="handleCartClick">
            <img src="@/assets/carrinho.png" alt="Cart" />
          </button>
          <button class="action-button user-button" @click="handleUserClick">
            <img src="@/assets/user.png" alt="User" />
          </button>
        </div>
      </header>
      <div class="infoprod">
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
            <button class="favorire-button" @click.stop="handleFavoriteClick">
              <font-awesome-icon icon="star" class="favoritocard" />
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
        <img
          :src="product.imagem"
          :alt="product.nome"
          class="product-image modalimage"
        />
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
        <div class="accordion-item">
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
      isOpen: {
        descricao: false,
        caracteristicas: false,
        metodoPreparo: false,
        outrosDetalhes: false,
      },
    };
  },
  watch: {
    isVisible(newValue) {
      if (newValue) {
        this.disableScroll();
      } else {
        this.enableScroll();
      }
    },
  },
  methods: {
    
    close() {
      this.$emit("close");
    },
    toggleAccordion(section) {
      this.isOpen[section] = !this.isOpen[section];
    },
    addToCart(product) {
      alert(`Produto ${product.nome} adicionado ao carrinho.`);
    },
    buyNow(product) {
      alert(`Comprando agora o produto ${product.nome}.`);
    },
    handleFavoriteClick() {
      alert("Produto adicionado aos favoritos.");
    },
    disableScroll() {
      document.body.classList.add("no-scroll");
    },
    enableScroll() {
      document.body.classList.remove("no-scroll");
    },
  },
};
</script>

<style>

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
  max-height: 80%; /* Define a altura máxima */
  overflow-y: auto; /* Adiciona rolagem vertical se o conteúdo ultrapassar a altura máxima */
  overflow-x: hidden; /* Adiciona rolagem vertical se o conteúdo ultrapassar a altura máxima */
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
  height: 2rem;
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
  height: 25%;
}
@import url("https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap");

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
  align-items: flex-start;
  font-family: "Poppins", sans-serif;
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

.add-to-cart,
.favorire-button {
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
</style>
