<template>
  <div class="map-container">
    <div ref="mapContainer" class="map"></div>
    <div class="elements-card">
      <h2>{{ truncatedAddress }}</h2>
      <div class="freight-calculation">
        <p v-if="freightCost !== null">Frete: R$ {{ freightCost.toFixed(2) }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faPlus, faEdit } from '@fortawesome/free-solid-svg-icons';

library.add(faPlus, faEdit);

export default {
  name: 'GoogleMap',
  components: {
    FontAwesomeIcon
  },
  props: {
    truncatedAddress: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      marker: null, // Armazena o marcador
      freightCost: null // Armazena o custo do frete calculado
    };
  },
  watch: {
    selectedAddress(newAddress) {
      if (newAddress) {
        console.log('Endereço selecionado mudou. Centralizando mapa:', newAddress);
        this.handleManualAddress(newAddress);
      }
    },
  },
  methods: {
    async initMap() {
      const mapOptions = {
        zoom: 8,
        center: { lat: -23.561732, lng: -46.655981 }, // Exemplo: Centro de São Paulo
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        disableDefaultUI: true, // Desativa todos os controles padrão
        zoomControl: false, // Desativa o controle de zoom
        streetViewControl: false, // Desativa o controle de Street View
        mapTypeControl: false, // Desativa o controle de tipo de mapa
        styles: [
          {
            featureType: 'all',
            elementType: 'labels',
            stylers: [{ visibility: 'off' }]
          }
        ]
      };

      this.map = new google.maps.Map(this.$refs.mapContainer, mapOptions);

      this.map.addListener('click', async (event) => {
        const latLng = event.latLng;
        const address = await this.reverseGeocode(latLng);
        this.$emit('addressClicked', address);

        // Adiciona ou move o marcador para a nova localização
        if (this.marker) {
          this.marker.setPosition(latLng);
        } else {
          this.marker = new google.maps.Marker({
            position: latLng,
            map: this.map,
            icon: {
              url: 'http://maps.google.com/mapfiles/ms/icons/red-dot.png' // Ícone vermelho
            }
          });
        }
      });
    },
    async reverseGeocode(latLng) {
      const geocoder = new google.maps.Geocoder();
      return new Promise((resolve, reject) => {
        geocoder.geocode({ location: latLng }, (results, status) => {
          if (status === 'OK' && results[0]) {
            resolve(results[0].formatted_address);
          } else {
            reject('No address found');
          }
        });
      });
    },
    loadGoogleMapsScript() {
      if (!window.google) {
        const apiKey = 'AIzaSyCY_iPNoPQJX4xCMDExpjF4PIFMxdxUIz8'; // Substitua com sua chave API real
        const script = document.createElement('script');
        script.src = `https://maps.googleapis.com/maps/api/js?key=${apiKey}&callback=initMap&libraries=places`;
        script.async = true;
        script.defer = true;
        document.head.appendChild(script);
        window.initMap = this.initMap.bind(this);
      } else {
        this.initMap();
      }
    },
    async centerMapOnAddress(address) {
      const geocoder = new google.maps.Geocoder();
      geocoder.geocode({ address }, (results, status) => {
        if (status === 'OK' && results[0]) {
          const location = results[0].geometry.location;
          this.map.setCenter(location);
          this.map.setZoom(15);

          if (this.marker) {
            this.marker.setPosition(location);
          } else {
            this.marker = new google.maps.Marker({
              position: location,
              map: this.map,
            });
          }
        } else {
          console.error('Erro ao geocodificar o endereço:', status);
        }
      });
    },
    async calculateFreight(address) {
      // Lógica para calcular o frete com base no CEP
      // Exemplo fictício de cálculo de frete
      const cep = address.split(',').pop().trim(); // Extrai o CEP do endereço
      if (cep) {
        this.freightCost = Math.random() * 100; // Substitua com a lógica real de cálculo de frete
      } else {
        this.freightCost = null;
      }
    },
    addAddress() {
      // Lógica para adicionar endereço
    },
    editAddress() {
      // Lógica para editar endereço
    }
  },
  mounted() {
    this.loadGoogleMapsScript();
  }
}
</script>

<style scoped>
.map-container {
  width: 100%; /* Ajusta a largura do elemento pai para 100% */
  position: relative; /* Define o pai como relativo */
}
.map {
  height: 12rem;
  width: 100% !important;
  margin-left: 0 !important;
  border-radius: 2rem 2rem 0 0;
}
.elements-card {
  background-color: #ffffff;
  border-radius: 0 0 2rem 2rem;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  margin: 0;
  font-size: 2rem;
  position: relative;
  z-index: 2;
  padding: 1rem;
  text-align: center;
  width: 100%;
  color: #000000;
}
.freight-calculation {
  display: flex;
  flex-direction: column;
  align-items: center;
  font-size: 1.5rem;
  color :#3a5bff;
}

</style>
