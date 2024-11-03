<template>
    <div class="notification-pop-up">
        Produto adicionado ao carrinho
    </div>
</template>

<script>
    export default{
        name: PopUpNotification,
    }
</script>

<style scoped>
.notification-pop-up{
    position:absolute;
    top: 25px;
    right: 30px;
    background-color: #ffffff;
    border-radius: 12px;
    padding: 20px 35px 20px 25px;
    box-shadow: 0 5px 10px rgba(0, 0, 0, 1);
}
</style>