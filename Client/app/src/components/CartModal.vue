<template>
    <div v-if=isModalVisible class="cart-modal-bckg" @click.self="close" >
        <div class="cart-modal">
            <div class="header-cart"> 
                <h1>Carrinho de compras</h1>
                <h1>Av. Dos Teste, No 123 - Bairro dos Testes</h1>
            </div>
            <div class="main-cart">
                <section class="products">
                <section v-for="(cartItem, index) in cartItems"
                :key = "index" 
                class="product-section">
                    <div class="image-bckg">
                        <img class="image-product" :src="cartItem.imagem_produto" :alt="cartItem.nome_produto">
                    </div>
                        <h2>{{cartItem.nome_produto}}</h2>
                        <button @click="removeItemOnCartUser(cartItem.userId, cartItem.produtoId, index)">REMOVER</button>
                        <select name="Quantidade" class="input-qntty">
                            <option :value="cartItem.quantidade">{{ cartItem.quantidade }}</option>
                            <option value="1">1 Uni</option>
                            <option value="2">2 Uni</option>
                            <option value="3">3 Uni</option>
                            <option value="4">4 Uni</option>
                        </select>
                        <span>R${{cartItem.preco_produto}}</span>
                </section>
                </section>
                <section class="frete-section">
                        <span id="info-01">Frete</span>
                        <span id="info-02">R$14,59</span>
                        <p id="info-03">Aproveite o <b>frete grátis</b> adicionando mais produtos<br style="display: hidden;"> ao pedido</p>
                </section>
            </div>
            <div class="footer-cart">
                <h3 id="info-ft-01"> 1 Produto</h3>
                <span id="info-ft-02">Inserir cupom</span>
                <span id="info-ft-03">R$90,00</span>
                <button class="btn-compra">Continuar a compra</button>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'

export default{
    name: 'CartModal',
    props:{
        isModalVisible: {
        type: Boolean,
        required: true,
    },
    data(){
        return{
            cartItems: [],
            cartValue: 0,
        };
    }
    },
    methods: {
        close(){
            this.$emit("close");
        },
        async fetchCarts(){
            console.log("Iniciando a requisição para o carrinho...");
            try{          
                const responseCart = await axios.get("http://localhost:8080/api/carrinho/1");
                this.cartItems = responseCart.data;
            } catch (error){
                console.error("Erro ao buscar produtos do carrinho: ", error);
            } finally {
                this.isLoading = false;
            }
        },
        async removeItemOnCartUser(userId, productId, index){
            console.log(userId, productId)
            try{
                const responseCart = await axios.delete(`http://localhost:8080/api/carrinho/${userId}/${productId}`);
                //await this.fetchCarts();
            } catch (error){
                console.error("Erro ao remover produto do carrinho", error);
            } finally {
                this.isLoading = false;
            }
    }
    },
    mounted(){
        this.fetchCarts();
    }
}
</script>

<style scoped>

*{
    font-size: 14pt;
}

.cart-modal-bckg{
    position:fixed;
    background-color: rgba(0, 0, 0, 0.7);
    margin-top: 6rem;
    left: 0;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: right;
    align-items: center;
    z-index: 20;
    backdrop-filter: blur(5px);
}

.cart-modal{
    background-color: #FFFFFF;
    width: 30%;
    height: 100%;
    z-index: 21;
    display: flex;
    flex-wrap: wrap;
    align-content: stretch;
}

.header-cart{
    background-color: #5170fc;
    width: 100%;
    height: 15%;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    font-size: 18pt;
    font-weight: bolder;
}

.cart-modal h1{
    font-size: 65%;
    color: black;
    font-weight: 100;
    margin: 10px 0px 0px 15%;
}

.main-cart{
    background-color: #c3c3c3 ;
    width: 95%;
    height: 40%;
    border-radius: 40px;
    margin: 0 auto 0;
    font-size: 18pt;
    overflow: hidden;
}

.main-cart h2{
    font-size: 16pt;
    color: black;
    font-weight: 100;
    
}

.products{
    height: 60%;
    overflow-y: auto;
}

.image-bckg{ grid-area: image;}
.product-section h2{ grid-area: name-product;}
.product-section button{ grid-area: btn-delete}
.product-section select{ grid-area: input-quantity;}
.product-section span{ grid-area: price-prod;}

.frete-section #info-01{ grid-area: span;}
.frete-section #info-02{ grid-area: price-frete;}
.frete-secttion #info-03{ grid-area: text-frete;}

#info-ft-01{ grid-area: qtty-product}
#info-ft-02{ grid-area: insert-cupom}
#info-ft-03{ grid-area: subtotal}
.btn-compra{ grid-area: btn-compra}

.product-section{
    padding: 20px 20px 0px 20px;
    overflow: hidden;
    display: grid;
    grid-template-columns: 30% 20% 10% 20% ;
    grid-template-rows: 3rem 1rem 2rem;
    grid-template-areas: 
    'image name-product input-quantity price-prod '
    'image btn-delete input-quantity price-prod';
    align-items: center;
    justify-items: center;
    justify-content: center;
    gap: 0.7rem 2rem;
    border-bottom: solid rgba(0, 0, 0, 0.342) 1pt;
}

button{
    background: #ea1d1dd3;
    border-radius: 4px;
    border-style: solid;
    border-color: #d70000;
    border-width: 1px;
    color: white;
    font-size: 11pt;
    
}

button:hover{
    background: hsla(0, 92%, 70%, 0.423);
    cursor: pointer;
    
}

.input-qntty{
    background: #d1d1d1;
    border: solid rgba(0, 0, 0, 0.748) 1px;
    border-radius: 50px;
    width: 4rem;
    font-size: 12pt;
    border-color: gray
}

.product-section > span{
    display: inline-block;

}

.image-bckg{
    background-color: gray;
    width: 4rem;
    height: 4rem;
    border-radius: 100%;
    display: flex;
    flex-direction: column;
    flex-wrap: wrap;
    align-content: center;
    justify-content: center;
    border-style: solid;
    border-color: black;
    border-width: 1px;   
}

.image-product{
    width: 50%;  
}

hr{
    border: solid rgba(0, 0, 0, 0.342) 1pt;
}

.frete-section{
    display: grid;
    grid-template-columns: 20% 33% 47%;
    grid-template-rows: 1rem 4rem;
    grid-template-areas: 
    'span none price-frete'
    'text-frente text-frete text-frete';
    gap: 0.7rem 2rem;
    justify-content: space-around;
    overflow: hidden;
    padding: 20px 20px 0px 20px;
    box-sizing: border-box;
}

#info-02{
    justify-self: end;
    padding-right: 4.5rem;
}

#info-03{
    width: 100%;
    height: 100%;
    grid-row: 2;
    grid-column: 1 / span 3;
    font-size: 10pt;
    box-sizing: border-box;
    overflow-wrap: break-word;
    white-space: normal;
    word-wrap: break-word;
}

.footer-cart{
    font-size: 18pt;
    background-color: #ffffff;
    width: 100%;
    height: 30%;    
    border-top-left-radius: 40px;
    border-top-right-radius: 40px;
    box-shadow: 0px -5px 3px gray;
    display: grid;
    grid-template-columns: 50% 50%;
    grid-template-rows: 10px 10px 40px;
    grid-template-areas: 
    'qtty-product none'
    'insert-cupom subtotal'
    'btn-compra btn-compra';
    box-sizing: border-box;
    gap: 2rem;
    padding: 0px;
    justify-content: center;
    justify-items: center;
}

.footer-cart > .btn-compra{
    justify-self: center;
    width: 80%;
    border-radius: 14px;
    padding: 30px auto auto 30px;
    background-color: #e3bf5f;
    border-color: #e3bf5f;
    color: black;
}



</style>