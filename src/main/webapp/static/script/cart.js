window.onload=function (){
    var vue = new Vue({
        el:"#cart_div",
        data:{
            cart:{}
        },
        methods:{
            getCart:function(){
                axios({
                    method:"POST",
                    url:"cart.do",
                    params:{
                        operate:"cartInfo"
                    }
                }).then(function(value){
                    var cart = value.data;
                    vue.cart = cart;
                    console.log(value)
                }).catch(function(reason){})
            },
            editCart:function (cartItemId, buyCount){
                if(buyCount <= 0){
                    alert("购买数量不能少于1！");
                    return;
                }

                axios({
                    method:"POST",
                    url:"cart.do",
                    params:{
                        operate:'editCart',
                        cartItemId:cartItemId,
                        buyCount:buyCount
                    }
                })
                    .then(function () {
                        vue.getCart();
                    })
                    .catch(function (reason) {  });
            },
            delCartItem:function (cartItemId){
                axios({
                    method:"POST",
                    url:"cart.do",
                    params:{
                        operate:'delCartItem',
                        cartItemId:cartItemId
                    }
                })
                    .then(function (){
                        vue.getCart();
                    })
                    .catch(function (reason){});

            }
        },
        mounted:function (){
            this.getCart();
        }

    });
}