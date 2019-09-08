/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * version 0.4 2077ver0.4
 * @param {type} obj
 * @returns {undefined}
 */
function PayButtom_onmouseover(obj) {
    obj.style.backgroundColor = '#124567';
}

function PayButtom_onmouseout(obj) {
    obj.style.backgroundColor = '#1B6EA6';
}

function PayButtom_onclick(obj) {
    obj.style.backgroundImage = 'Linear-gradient(45deg, #1B6EA6, #22AF6C)';
}