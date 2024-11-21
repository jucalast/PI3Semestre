<template>
  <div ref="mapContainer" class="map-container"></div>
</template>

<script>
export default {
  name: 'GoogleMap',
  methods: {
    async initMap() {
      const mapOptions = {
        zoom: 8,
        center: { lat: -23.561732, lng: -46.655981 }, // Exemplo: Centro de São Paulo
        mapTypeId: google.maps.MapTypeId.ROADMAP
      };

      this.map = new google.maps.Map(this.$refs.mapContainer, mapOptions);

      this.map.addListener('click', async (event) => {
        const latLng = event.latLng;
        const address = await this.reverseGeocode(latLng);
        this.$emit('addressClicked', address);
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
      return new Promise((resolve, reject) => {
        geocoder.geocode({ address }, (results, status) => {
          if (status === 'OK' && results[0]) {
            const location = results[0].geometry.location;

            // Centraliza o mapa no endereço
            this.map.setCenter(location);

            // Ajusta o nível de zoom para aproximar
            this.map.setZoom(15); // Escolha um valor de zoom adequado (15 é uma boa aproximação para locais urbanos)

            // Adiciona um marcador no local
            new google.maps.Marker({
              position: location,
              map: this.map,
            });

            resolve(location);
          } else {
            reject('Falha ao obter a localização.');
          }
        });
      });
    }
  },
  mounted() {
    this.loadGoogleMapsScript();
  }
}
</script>

<style scoped>
.map-container {
  height: 300px;
  width: 100%;
}
</style>
