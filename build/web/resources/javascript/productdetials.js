//change images
//version 0.4 2077ver0.4
var imageId = 1;
var max_image_number = 2;
var min_image_number = 1;

function right_changeImage() {
    if (imageId < max_image_number) {
        imageId++;
    }
    document.getElementById("Product_IMG").src = 'imgs/productIMG/product' + imageId + '.png';

    console.log('id' + imageId);
}

function left_changeImage() {
    if (imageId > min_image_number) {
        imageId--;
    }
    document.getElementById("Product_IMG").src = 'imgs/productIMG/product' + imageId + '.png';

    console.log('id' + imageId);
}


//“加入购物车”按钮
function ProductCartButtom_onmouseover(obj) {
    obj.style.backgroundColor = '#124567';
}

function ProductCartButtom_onmouseout(obj) {
    obj.style.backgroundColor = '#1B6EA6';
}

function ProductCartButtom_onclick(obj) {
    obj.style.backgroundImage = 'Linear-gradient(45deg, #1B6EA6, #22AF6C)';
    obj.innerHTML = "已加入购物车";
}


//“切换商品预览图” 及动态样式
function ProductIMG_trans_onmouseover() {
    var image1 = document.getElementById("ProductImgChange_left");
    var image2 = document.getElementById("ProductImgChange_right");

    image1.style.opacity = 0.8;
    image2.style.opacity = 0.8;

    image1.style.backgroundImage = 'Linear-grandient(90deg, #BBBABA, #D3D3D3)';
    image2.style.backgroundImage = 'Linear-grandient(270deg, #BBBABA, #D3D3D3)';
}

function ProductIMG_trans_onmouseout() {
    var image1 = document.getElementById("ProductImgChange_left");
    var image2 = document.getElementById("ProductImgChange_right");

    image1.style.opacity = 0.3;
    image2.style.opacity = 0.3;

    image1.style.backgroundImage = null;
    image2.style.backgroundImage = null;
}

//
function ProductIMG_change_onmouseover(obj) {
    obj.style.opacity = 0.8;
    obj.style.backgroundImage = null;
    obj.style.backgroundColor = '#D8D8D8';
}

function ProductIMG_lchange_onmouseout(obj) {
    obj.style.opacity = 0.3;
    obj.style.backgroundColor = null;
    obj.style.backgroundImage = 'linear-gradient(90deg, #D8D8D8, transparent)';
}

function ProductIMG_rchange_onmouseout(obj) {
    obj.style.opacity = 0.3;
    obj.style.backgroundColor = null;
    obj.style.backgroundImage = 'linear-gradient(270deg, #D8D8D8, transparent)';
}

//改变选购数量 的按钮
var num = 1;
var min_inventory = 1;
var max_inventory = 10;

function ProductNum_plus() {
    if(num < max_inventory)
    num ++;
    document.getElementById("countNum:ProductBuyNumber_TXT").value = num;
    console.log(num);
}

function ProductNum_minus() {
    if(num > min_inventory)
    num --;
    document.getElementById("countNum:ProductBuyNumber_TXT").value = num;
    console.log(num);
}