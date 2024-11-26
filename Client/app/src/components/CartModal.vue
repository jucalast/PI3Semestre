<template>
  <div v-if="isModalVisible" class="cart-modal-bckg" @click.self="close">
    <div class="cart-modal">
      <div class="header-cart">
        <h1>Carrinho de compras</h1>
      </div>
      <div class="main-cart">
        <section class="products">
          <section
              v-for="(cartItem, index) in cartItems"
              :key="index"
              class="product-section"
          >
            <div class="image-bckg">
              <img
                class="image-product"
                :src="cartItem.imagens ? cartItem.imagens[0] : ''"
                :alt="cartItem.nome"
              />
            </div>
            <div class="nameandbutton">
              <h2>{{ cartItem.nome }}</h2>
              <button class="excluir" @click="removeItemOnCartUser(cartItem.produtoId)">
                Excluir
              </button>
            </div>
            <select
                @change="
                updateQuantidadeItem(cartItem.produtoId, $event.target.value)
              "
                name="Quantidade"
                class="input-qntty"
                :value="cartItem.quantidade"
            >
              <option value="1">1</option>
              <option value="2">2</option>
              <option value="3">3</option>
              <option value="4">4</option>
              <option value="5">5</option>
              <option value="6">6</option>
              <option value="7">7</option>
              <option value="8">8</option>
            </select>
            <span>R${{ cartItem.preco * cartItem.quantidade }}</span>
          </section>
        </section>
      </div>

      <div class="footer-cart">
        <div class="info-footer">
          <div class="info-left">
            <h3 id="info-ft-01">{{ somaQuantidade }} Produtos</h3>
          </div>

          <div class="info-rigth">
            <span id="info-ft-03">
              Total: R${{ parseFloat(somaValorItens.toFixed(2)) }}
            </span>
          </div>
        </div>
        <section v-if="false" class="frete-section">
          <div class="frete">
            <span id="info-01">Frete</span>
            <span id="info-02">R$14,59</span>
          </div>
          <p id="info-03">
            Aproveite o
            <strong>frete grátis</strong>
            adicionando mais produtos ao pedido
          </p>
        </section>
        <button class="btn-compra" @click="continueToCheckout">Continuar a compra</button>
      </div>
    </div>
  </div>
</template>


<script>
import axiosInstance from '@/utils/axiosInstance';

export default {
  name: 'CartModal',
  props: {
    isModalVisible: {
      type: Boolean,
      required: true,
    },
  },
  watch: {
    isModalVisible(newValue) {
      if (newValue) {
        this.fetchCarts();
        document.body.style.overflowY = 'hidden'; // Desativa o overflow-y do body
      } else {
        document.body.style.overflowY = 'auto'; // Reativa o overflow-y do body
      }
    },
  },
  data() {
    return {
      cartItems: [],
      somaValorItens: 0,
      somaQuantidade: 0,
    };
  },
  methods: {
    close() {
      this.$emit('close');
      document.body.style.overflowY = 'auto'; // Reativa o overflow-y do body
    },
    async fetchCarts() {
      try {
        const responseCart = await axiosInstance.get(`/api/carrinho/`);
        this.cartItems = responseCart.data.map(item => ({
          produtoId: item.produtoId,
          imagens: item.imagens,
          nome: item.nome,
          preco: item.preco,
          quantidade: item.quantidade
        }));
        this.somasCarrinho(this.cartItems);
      } catch (error) {
        console.error('Erro ao buscar produtos do carrinho: ', error);
        return;
      }
    },
    async removeItemOnCartUser(productId) {
      try {
        const responseCart = await axiosInstance.delete(`/api/carrinho/${productId}`);
        this.fetchCarts();
      } catch (error) {
        console.error('Erro ao remover produto do carrinho', error);
      }
    },
    somasCarrinho(itensCarrinho) {
      this.somaValorItens = 0;
      this.somaQuantidade = 0;
      itensCarrinho.forEach(item => {
        this.somaValorItens += parseFloat(item.preco) * parseInt(item.quantidade); // Corrige o cálculo do preço
        this.somaQuantidade += parseInt(item.quantidade);
      });
    },
    async updateQuantidadeItem(productId, quantity) {
      const responseCart = await axiosInstance.put(
          `/api/carrinho/${productId}/${parseInt(quantity)}`
      );
      this.fetchCarts();
    },
    async addToCart(product) {
      try {
        const response = await axiosInstance.post(`/api/carrinho/${product.id}`);
        if (response.status === 200) {
          this.fetchCarts();
          alert(`Produto ${product.nome} adicionado ao carrinho.`);
        } else {
          alert('Erro ao adicionar produto ao carrinho.');
        }
      } catch (error) {
        console.error('Erro ao adicionar produto ao carrinho:', error);
        alert('Erro ao adicionar produto ao carrinho.');
      }
    },
    async continueToCheckout() {
      try {
        const response = await axiosInstance.get('/api/carrinho/user-id');
        const userId = response.data;
        this.$router.push({ name: 'Checkout', query: { userId } });
      } catch (error) {
        console.error('Erro ao obter o ID do usuário:', error);
        alert('Erro ao continuar para o checkout.');
      }
    }
  },
  async mounted() {
    await this.fetchCarts();
  },
};
</script>

<style scoped>
  * {
    font-size: 14pt;
  }

.cart-modal-bckg {
  position: fixed;
  background-color: rgba(0, 0, 0, 0.7);
  left: 0;
  bottom: 0;
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: right;
  align-items: center;
  z-index: 20;
  backdrop-filter: blur(5px);
}

  .cart-modal {
    background-color: #ffffff;
    width: 35%;
    height: 100vh;
    z-index: 21;
    display: flex;
    flex-wrap: wrap;
    align-content: stretch;
  }

  .header-cart {
    background-color: #5170fc;
    width: 100%;
    height: 10%;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    font-weight: bolder;
    align-content: center;
    justify-content: flex-start;
    padding: 1rem;
    gap: 1rem;
  }

  .cart-modal h1 {
    font-size: 1.5rem;
    color: black;
    font-weight: 100;
    margin: 0px 0px 0px 10%;
  }

  .main-cart {
    width: 95%;
    height: 63%;

    margin: 0 auto 0;
    font-size: 18pt;
    overflow: hidden;
  }

  .main-cart h2 {
    font-size: 16pt;
    color: black;
    font-weight: 100;
  }

  .products {
    height: 100%;
    overflow-y: auto;
  }

  .image-bckg {
    grid-area: image;
  }
  .product-section h2 {
    padding-left: 1rem;
    grid-area: name-product;
  }
  .product-section button {
    grid-area: btn-delete;
  }
  .product-section select {
    grid-area: input-quantity;
    height: 3rem;
    width: 6rem;
    padding: 0.5rem;
    border: solid 1px #aeaeaeb6;
    background: #ededed;
  }
  .product-section select:focus {
    outline: none;
  }
  .product-section span {
    grid-area: price-prod;
  }

  .frete-section #info-01 {
    grid-area: span;
  }

  .frete-section #info-02 {
    grid-area: price-frete;
  }
  .frete-secttion #info-03 {
    grid-area: text-frete;
  }

  #info-ft-01 {
    grid-area: qtty-product;
  }
  #info-ft-02 {
    grid-area: insert-cupom;
  }
  #info-ft-03 {
    grid-area: subtotal;
  }
  .btn-compra {
    grid-area: btn-compra;
  }

  .product-section {
    background: #dfdfdf !important;
    overflow: hidden;
    margin: 2rem;
    display: flex;
    padding: 2rem;
    align-items: center;
    justify-items: center;
    justify-content: space-between;
    gap: 0.7rem 2rem;
    border-radius: 2rem;
  }

  .input-qntty {
    background: #d1d1d1;
    border: solid rgba(0, 0, 0, 0.748) 1px;
    border-radius: 50px;
    width: 4rem;
    font-size: 12pt;
    border-color: gray;
  }

  .product-section > span {
    display: inline-block;
  }

  .image-bckg {
    width: 7rem;
    border-radius: 100%;
    display: flex;
    flex-direction: column;
    flex-wrap: wrap;
    align-content: center;
    justify-content: center;
  }

  .image-product {
    width: 100%;
  }

  hr {
    border: solid rgba(0, 0, 0, 0.342) 1pt;
  }

  .frete-section {
    display: flex;
    gap: 0.7rem 2rem;
    justify-content: space-around;
    overflow: hidden;
    width: 80%;
    box-sizing: border-box;
    align-items: center;
    height: 30%;
    flex-direction: column;
  }

  #info-02 {
    justify-self: end;
  }
  #info-01 {
    height: fit-content;
    color: #5170fc;
  }

  .excluir {
    color: #ff4d4d;
    background: transparent;
    border: none;
    font-size: 1.5rem;
    padding: 1rem;
    border-radius: 2rem;
  }

  .excluir:hover {
    background: #ff4d4d2f;
  }
  #info-03 {
    background-color: transparent;
    width: 100%;
    font-size: 1.2rem !important;
  }

  strong {
    font-size: 1.2rem !important;
  }

  .nameandbutton {
    display: flex;
    align-items: flex-start;
    flex-direction: column;
  }

  .footer-cart {
    background-color: #ffffff;
    width: 100%;
    /*height: 45%;*/
    border-top-left-radius: 40px;
    border-top-right-radius: 40px;
    box-shadow: 0px -1px 70px rgba(128, 128, 128, 0.701);
    display: flex;
    gap: 1.5rem;
    padding: 0 0 3rem 0;
    flex-direction: column;
    align-items: center;
  }

  .btn-compra {
    justify-self: center;
    width: 80%;
    height: 5rem;
    border-radius: 14px;
    background-color: #e3bf5f;
    border-color: #e3bf5f;
    color: #403619;
    font-size: 2rem;
  }

  .btn-compra:hover {
    background-color: #af944a;
    border-color: #e3bf5f;
    color: #403619;
  }

  .info-rigth {
    display: flex;
    width: 50%;
    height: 2rem;
    flex-direction: column;
    align-content: center;
    justify-content: space-around;
    align-items: center;
    align-items: flex-end;
    margin-right: 2rem;
  }
  .info-left {
    display: flex;
    width: 50%;
    height: 2rem;
    flex-direction: column;
    align-content: center;
    justify-content: flex-start;
    align-items: flex-start;
    margin-left: 2rem;
    margin-top: 1rem;
    font-size: 0.5rem !important;
  }
  .info-footer {
    width: 100%;
    display: flex;
    height: 25%;
    align-items: center;
    padding: 2rem;
  }

  .frete {
    display: flex;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    width: 100%;
  }
</style>
