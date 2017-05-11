 (function (doc, win) {    
            var docEl = doc.documentElement,    
            resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',    
            recalc = function () {    
            var clientWidth = docEl.clientWidth;    
            if (!clientWidth) return;    
            //1 rem = 10 px in iphone6(375x667px)
            //so, it will zoom in/out in different resolution device.
            docEl.style.fontSize = 20 * (clientWidth / 375) + 'px';    
            //why use 375(iphone6)? it is just a custom, you can use 320(iphone5), too. no prolbem, it is just your standard.
            //but if it is fixed, the fontSize will be the benchmark, other device zoom in/out will all basis on the benchmark.
            //let me give example:
            //i define 1rem=20px in iphone6, i put a text whose font is 2 rem, then its font-size is 40px;
            //but when i switch to ipad(768), 1 rem now change to (768/375)*20 = 1rem, which is 1rem=20px, so the 2rem value now change to 40.
        };    
        if (!doc.addEventListener) return;    
        win.addEventListener(resizeEvt, recalc, false);    
        doc.addEventListener('DOMContentLoaded', recalc, false);    
        })(document, window); 