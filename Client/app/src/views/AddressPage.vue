<template>
    <DefaultLayout>
        <div class="address-page p-6 bg-gray-100 min-h-screen flex flex-col">
            <h1 class="text-3xl font-bold mb-6 text-gray-800">Gerenciar Endereços</h1>

            <form @submit.prevent="submitAddress" class="bg-white p-6 rounded-lg shadow-md mb-6 flex-grow">
                <h2 class="text-2xl font-semibold mb-4">Adicionar/Editar Endereço</h2>

                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div class="col-span-1">
                        <label class="block text-gray-700" for="zipCode">CEP</label>
                        <input type="text" v-model="address.zipCode" id="zipCode" @blur="fetchAddressByZipCode" required class="input-cep" />
                    </div>

                    <div>
                        <label class="block text-gray-700" for="street">Rua</label>
                        <input type="text" v-model="address.street" id="street" required class="input-field" />
                    </div>

                    <div>
                        <label class="block text-gray-700" for="number">Número</label>
                        <input type="text" v-model="address.number" id="number" required class="input-field" />
                    </div>

                    <div>
                        <label class="block text-gray-700" for="neighborhood">Bairro</label>
                        <input type="text" v-model="address.neighborhood" id="neighborhood" required class="input-field" />
                    </div>

                    <div>
                        <label class="block text-gray-700" for="city">Cidade</label>
                        <input type="text" v-model="address.city" id="city" required class="input-field" />
                    </div>

                    <div>
                        <label class="block text-gray-700" for="state">Estado</label>
                        <input type="text" v-model="address.state" id="state" required class="input-field" />
                    </div>
                </div>

                <div class="flex mt-6">
                    <button type="submit"
                        class="mr-4 px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition duration-200">
                        {{ isEditing ? 'Atualizar Endereço' : 'Adicionar Endereço' }}
                    </button>
                    <button v-if="isEditing" type="button" @click="cancelEdit"
                        class="px-4 py-2 bg-gray-300 text-gray-700 rounded-md hover:bg-gray-400 transition duration-200">
                        Voltar
                    </button>
                </div>
            </form>

            <h2 class="text-2xl font-semibold mb-4">Endereços Cadastrados</h2>
            <ul class="space-y-4">
                <li v-for="addr in addresses" :key="addr.id"
                    class="bg-white p-4 rounded-md shadow hover:bg-gray-50 transition duration-200 flex justify-between items-center">
                    <div>
                        <strong>{{ addr.street }}, {{ addr.number }}</strong><br />
                        {{ addr.neighborhood }}, {{ addr.city }} - {{ addr.state }} ({{ addr.zipCode }})
                    </div>
                    <div>
                        <button @click="editAddress(addr)" class="text-blue-600 hover:underline">Editar</button>
                        <button @click="deleteAddress(addr.id)" class="ml-4 text-red-600 hover:underline">Remover</button>
                    </div>
                </li>
            </ul>
        </div>
    </DefaultLayout>
</template>

<script>
import axiosInstance from '@/utils/axiosInstance';
import axios from 'axios';
import DefaultLayout from '@/layouts/DefaultLayout.vue';

export default {
    components: {
        DefaultLayout,
    },
    data() {
        return {
            addresses: [],
            address: {
                street: '',
                number: '',
                neighborhood: '',
                city: '',
                state: '',
                zipCode: '',
            },
            isEditing: false,
            currentAddressId: null,
        };
    },
    created() {
        this.fetchAddresses();
    },
    methods: {
        async fetchAddresses() {
            try {
                const response = await axiosInstance.get('/api/addresses');
                this.addresses = response.data;
            } catch (error) {
                console.error('Erro ao buscar endereços:', error);
            }
        },
        async fetchAddressByZipCode() {
            if (this.address.zipCode.length === 8) { 
                try {
                    const response = await axios.get(`https://viacep.com.br/ws/${this.address.zipCode}/json/`);
                    if (response.data && !response.data.erro) {
                        this.address.street = response.data.logradouro;
                        this.address.neighborhood = response.data.bairro;
                        this.address.city = response.data.localidade;
                        this.address.state = response.data.uf;
                    } else {
                        alert('CEP não encontrado.');
                    }
                } catch (error) {
                    console.error('Erro ao buscar endereço:', error);
                }
            }
        },
        async submitAddress() {
            try {
                if (this.isEditing) {
                    await axiosInstance.put(`/api/addresses/${this.currentAddressId}`, this.address);
                } else {
                    await axiosInstance.post('/api/addresses', this.address);
                }
                this.resetForm();
                this.fetchAddresses();
            } catch (error) {
                console.error('Erro ao salvar endereço:', error);
            }
        },
        editAddress(address) {
            this.address = { ...address };
            this.currentAddressId = address.id;
            this.isEditing = true;
        },
        async deleteAddress(id) {
            if (confirm('Tem certeza que deseja remover este endereço?')) {
                try {
                    await axiosInstance.delete(`/api/addresses/${id}`);
                    this.fetchAddresses();
                } catch (error) {
                    console.error('Erro ao remover endereço:', error);
                }
            }
        },
        resetForm() {
            this.address = {
                street: '',
                number: '',
                neighborhood: '',
                city: '',
                state: '',
                zipCode: '',
            };
            this.isEditing = false;
            this.currentAddressId = null;
        },
        cancelEdit() {
            this.resetForm();
        },
    },
};
</script>

<style scoped>
.input-field {
    margin-top: 0.25rem;
    display: block;
    width: 100%;
    height: 3rem;
    border: 1px solid #D1D5DB;
    border-radius: 0.375rem;
    box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
    padding: 0.5rem;
}

.input-field:focus {
    border-color: #D97706; 
    outline: none;
    box-shadow: 0 0 0 0.2rem rgba(255, 165, 0, 0.25);
}

.input-cep {
    margin-top: 0.25rem;
    display: block;
    width: 100%;
    height: 3rem;
    border: 1px solid #D97706; 
    border-radius: 0.375rem;
    box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
    padding: 0.5rem;
    background-color: #FFEDD5; 
}

.input-cep:focus {
    border-color: #B45309; 
    outline: none;
    box-shadow: 0 0 0 0.2rem rgba(255, 140, 0, 0.25); 
}
</style>
