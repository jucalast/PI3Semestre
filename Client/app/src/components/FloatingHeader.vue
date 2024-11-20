<template>
  <div class="floating-header">
    <ul>
      <li><button @click="$emit('open-image-upload')" class="customize-button"><i class="fas fa-sliders-h"></i></button></li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'FloatingHeader',
};
</script>

<style scoped>
.floating-header {
    display: flex
;
    position: fixed;
    top: 10%;
    left: 2%;
    transform: translateY(-50%);
    background-color: #ffffff64;
    border-radius: 50%;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    backdrop-filter: blur(10px);
    padding: 1rem;
    width: 7rem;
    z-index: 1000;
    justify-content: center;
}

.floating-header ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.floating-header li {
  margin: 1rem 0;
}

.floating-header a {
  text-decoration: none;
  color: #ffffff;
  font-weight: bold;
}

.floating-header a:hover {
  color: #5f75e4;
}

.customize-button {
  background: none;
  border: none;
  cursor: pointer;
  font-size: 2rem;
  color: #162d9f;
}

.customize-button:hover {
  color: #37458b;
}
</style>