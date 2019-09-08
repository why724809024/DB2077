a = document.getElementsByClassName('pdcountsum'); b = 0;
for (let i = 0; i < a.length; i++) { b += Number(a[i].innerHTML) };
document.getElementsByClassName('CartTotalCost')[0].value = b.toFixed(2);

function CmtSubmit(obj) {
    cmtas = document.getElementsByClassName('cmtDisplay');
    strs = '';
    for (i=0; i<cmtas.length; i++) {
        strs+=cmtas[i].value+';';
    }
    document.getElementsByClassName('cmtReal')[0].value = strs;
    // alert(strs);
    return true;
};