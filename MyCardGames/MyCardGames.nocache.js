function mycardgames(){var Q='bootstrap',R='begin',S='gwt.codesvr.mycardgames=',T='gwt.codesvr=',U='mycardgames',V='startup',W='DUMMY',X=0,Y=1,Z='iframe',$='position:absolute; width:0; height:0; border:none; left: -1000px;',_=' top: -1000px;',ab='CSS1Compat',bb='<!doctype html>',cb='',db='<html><head><\/head><body><\/body><\/html>',eb='undefined',fb='readystatechange',gb=10,hb='Chrome',ib='eval("',jb='");',kb='script',lb='javascript',mb='moduleStartup',nb='moduleRequested',ob='Failed to load ',pb='head',qb='meta',rb='name',sb='mycardgames::',tb='::',ub='gwt:property',vb='content',wb='=',xb='gwt:onPropertyErrorFn',yb='Bad handler "',zb='" for "gwt:onPropertyErrorFn"',Ab='gwt:onLoadErrorFn',Bb='" for "gwt:onLoadErrorFn"',Cb='#',Db='?',Eb='/',Fb='img',Gb='clear.cache.gif',Hb='baseUrl',Ib='mycardgames.nocache.js',Jb='base',Kb='//',Lb='locale',Mb='default',Nb='locale=',Ob=7,Pb='&',Qb='__gwt_Locale',Rb='_',Sb='Unexpected exception in locale detection, using default: ',Tb='user.agent',Ub='webkit',Vb='safari',Wb='msie',Xb=11,Yb='ie10',Zb=9,$b='ie9',_b=8,ac='ie8',bc='gecko',cc='gecko1_8',dc=2,ec=3,fc=4,gc='selectingPermutation',hc='mycardgames.devmode.js',ic='de',jc='0F9218522A7C9B97CDACF35017D27C42',kc='12B2DB5AAE4554E89C00E168F487392F',lc='5A9289559E5DD638DA8E179D25EC76F8',mc='8AF94C62AC921E541FBCB66B68995BFB',nc='B694A05E9AEA9E336605633709446E11',oc='C2F04FF47BF7E8667C9E7F50F3773CBB',pc='C69AC01A8DFD1EA412DF00C11C49A329',qc='D21A2394C00EB0011C58187080A7FE7A',rc='D28123AA672F4E52D754109CD647D4F1',sc='F8863E90DFF041D46F21F473DFD738C2',tc=':',uc='.cache.js',vc='link',wc='rel',xc='stylesheet',yc='href',zc='loadExternalRefs',Ac='gwt/standard/standard.css',Bc='end',Cc='http:',Dc='file:',Ec='_gwt_dummy_',Fc='__gwtDevModeHook:mycardgames',Gc='Ignoring non-whitelisted Dev Mode URL: ',Hc=':moduleBase';var q=window;var r=document;t(Q,R);function s(){var a=q.location.search;return a.indexOf(S)!=-1||a.indexOf(T)!=-1}
function t(a,b){if(q.__gwtStatsEvent){q.__gwtStatsEvent({moduleName:U,sessionId:q.__gwtStatsSessionId,subSystem:V,evtGroup:a,millis:(new Date).getTime(),type:b})}}
mycardgames.__sendStats=t;mycardgames.__moduleName=U;mycardgames.__errFn=null;mycardgames.__moduleBase=W;mycardgames.__softPermutationId=X;mycardgames.__computePropValue=null;mycardgames.__getPropMap=null;mycardgames.__installRunAsyncCode=function(){};mycardgames.__gwtStartLoadingFragment=function(){return null};mycardgames.__gwt_isKnownPropertyValue=function(){return false};mycardgames.__gwt_getMetaProperty=function(){return null};var u=null;var v=q.__gwt_activeModules=q.__gwt_activeModules||{};v[U]={moduleName:U};mycardgames.__moduleStartupDone=function(e){var f=v[U].bindings;v[U].bindings=function(){var a=f?f():{};var b=e[mycardgames.__softPermutationId];for(var c=X;c<b.length;c++){var d=b[c];a[d[X]]=d[Y]}return a}};var w;function A(){B();return w}
function B(){if(w){return}var a=r.createElement(Z);a.id=U;a.style.cssText=$+_;a.tabIndex=-1;r.body.appendChild(a);w=a.contentWindow.document;w.open();var b=document.compatMode==ab?bb:cb;w.write(b+db);w.close()}
function C(k){function l(a){function b(){if(typeof r.readyState==eb){return typeof r.body!=eb&&r.body!=null}return /loaded|complete/.test(r.readyState)}
var c=b();if(c){a();return}function d(){if(!c){if(!b()){return}c=true;a();if(r.removeEventListener){r.removeEventListener(fb,d,false)}if(e){clearInterval(e)}}}
if(r.addEventListener){r.addEventListener(fb,d,false)}var e=setInterval(function(){d()},gb)}
function m(c){function d(a,b){a.removeChild(b)}
var e=A();var f=e.body;var g;if(navigator.userAgent.indexOf(hb)>-1&&window.JSON){var h=e.createDocumentFragment();h.appendChild(e.createTextNode(ib));for(var i=X;i<c.length;i++){var j=window.JSON.stringify(c[i]);h.appendChild(e.createTextNode(j.substring(Y,j.length-Y)))}h.appendChild(e.createTextNode(jb));g=e.createElement(kb);g.language=lb;g.appendChild(h);f.appendChild(g);d(f,g)}else{for(var i=X;i<c.length;i++){g=e.createElement(kb);g.language=lb;g.text=c[i];f.appendChild(g);d(f,g)}}}
mycardgames.onScriptDownloaded=function(a){l(function(){m(a)})};t(mb,nb);var n=r.createElement(kb);n.src=k;if(mycardgames.__errFn){n.onerror=function(){mycardgames.__errFn(U,new Error(ob+code))}}r.getElementsByTagName(pb)[X].appendChild(n)}
mycardgames.__startLoadingFragment=function(a){return G(a)};mycardgames.__installRunAsyncCode=function(a){var b=A();var c=b.body;var d=b.createElement(kb);d.language=lb;d.text=a;c.appendChild(d);c.removeChild(d)};function D(){var c={};var d;var e;var f=r.getElementsByTagName(qb);for(var g=X,h=f.length;g<h;++g){var i=f[g],j=i.getAttribute(rb),k;if(j){j=j.replace(sb,cb);if(j.indexOf(tb)>=X){continue}if(j==ub){k=i.getAttribute(vb);if(k){var l,m=k.indexOf(wb);if(m>=X){j=k.substring(X,m);l=k.substring(m+Y)}else{j=k;l=cb}c[j]=l}}else if(j==xb){k=i.getAttribute(vb);if(k){try{d=eval(k)}catch(a){alert(yb+k+zb)}}}else if(j==Ab){k=i.getAttribute(vb);if(k){try{e=eval(k)}catch(a){alert(yb+k+Bb)}}}}}__gwt_getMetaProperty=function(a){var b=c[a];return b==null?null:b};u=d;mycardgames.__errFn=e}
function F(){function e(a){var b=a.lastIndexOf(Cb);if(b==-1){b=a.length}var c=a.indexOf(Db);if(c==-1){c=a.length}var d=a.lastIndexOf(Eb,Math.min(c,b));return d>=X?a.substring(X,d+Y):cb}
function f(a){if(a.match(/^\w+:\/\//)){}else{var b=r.createElement(Fb);b.src=a+Gb;a=e(b.src)}return a}
function g(){var a=__gwt_getMetaProperty(Hb);if(a!=null){return a}return cb}
function h(){var a=r.getElementsByTagName(kb);for(var b=X;b<a.length;++b){if(a[b].src.indexOf(Ib)!=-1){return e(a[b].src)}}return cb}
function i(){var a=r.getElementsByTagName(Jb);if(a.length>X){return a[a.length-Y].href}return cb}
function j(){var a=r.location;return a.href==a.protocol+Kb+a.host+a.pathname+a.search+a.hash}
var k=g();if(k==cb){k=h()}if(k==cb){k=i()}if(k==cb&&j()){k=e(r.location.href)}k=f(k);return k}
function G(a){if(a.match(/^\//)){return a}if(a.match(/^[a-zA-Z]+:\/\//)){return a}return mycardgames.__moduleBase+a}
function H(){var i=[];var j=X;function k(a,b){var c=i;for(var d=X,e=a.length-Y;d<e;++d){c=c[a[d]]||(c[a[d]]=[])}c[a[e]]=b}
var l=[];var m=[];function n(a){var b=m[a](),c=l[a];if(b in c){return b}var d=[];for(var e in c){d[c[e]]=e}if(u){u(a,d,b)}throw null}
m[Lb]=function(){var b=null;var c=Mb;try{if(!b){var d=location.search;var e=d.indexOf(Nb);if(e>=X){var f=d.substring(e+Ob);var g=d.indexOf(Pb,e);if(g<X){g=d.length}b=d.substring(e+Ob,g)}}if(!b){b=__gwt_getMetaProperty(Lb)}if(!b){b=q[Qb]}if(b){c=b}while(b&&!__gwt_isKnownPropertyValue(Lb,b)){var h=b.lastIndexOf(Rb);if(h<X){b=null;break}b=b.substring(X,h)}}catch(a){alert(Sb+a)}q[Qb]=c;return b||Mb};l[Lb]={'de':X,'default':Y};m[Tb]=function(){var a=navigator.userAgent.toLowerCase();var b=r.documentMode;if(function(){return a.indexOf(Ub)!=-1}())return Vb;if(function(){return a.indexOf(Wb)!=-1&&(b>=gb&&b<Xb)}())return Yb;if(function(){return a.indexOf(Wb)!=-1&&(b>=Zb&&b<Xb)}())return $b;if(function(){return a.indexOf(Wb)!=-1&&(b>=_b&&b<Xb)}())return ac;if(function(){return a.indexOf(bc)!=-1||b>=Xb}())return cc;return cb};l[Tb]={'gecko1_8':X,'ie10':Y,'ie8':dc,'ie9':ec,'safari':fc};__gwt_isKnownPropertyValue=function(a,b){return b in l[a]};mycardgames.__getPropMap=function(){var a={};for(var b in l){if(l.hasOwnProperty(b)){a[b]=n(b)}}return a};mycardgames.__computePropValue=n;q.__gwt_activeModules[U].bindings=mycardgames.__getPropMap;t(Q,gc);if(s()){return G(hc)}var o;try{k([ic,$b],jc);k([Mb,$b],kc);k([Mb,Vb],lc);k([Mb,ac],mc);k([ic,Yb],nc);k([ic,cc],oc);k([ic,Vb],pc);k([ic,ac],qc);k([Mb,cc],rc);k([Mb,Yb],sc);o=i[n(Lb)][n(Tb)];var p=o.indexOf(tc);if(p!=-1){j=parseInt(o.substring(p+Y),gb);o=o.substring(X,p)}}catch(a){}mycardgames.__softPermutationId=j;return G(o+uc)}
function I(){if(!q.__gwt_stylesLoaded){q.__gwt_stylesLoaded={}}function c(a){if(!__gwt_stylesLoaded[a]){var b=r.createElement(vc);b.setAttribute(wc,xc);b.setAttribute(yc,G(a));r.getElementsByTagName(pb)[X].appendChild(b);__gwt_stylesLoaded[a]=true}}
t(zc,R);c(Ac);t(zc,Bc)}
D();mycardgames.__moduleBase=F();v[U].moduleBase=mycardgames.__moduleBase;var J=H();if(q){var K=!!(q.location.protocol==Cc||q.location.protocol==Dc);q.__gwt_activeModules[U].canRedirect=K;function L(){var b=Ec;try{q.sessionStorage.setItem(b,b);q.sessionStorage.removeItem(b);return true}catch(a){return false}}
if(K&&L()){var M=Fc;var N=q.sessionStorage[M];if(!/^http:\/\/(localhost|127\.0\.0\.1)(:\d+)?\/.*$/.test(N)){if(N&&(window.console&&console.log)){console.log(Gc+N)}N=cb}if(N&&!q[M]){q[M]=true;q[M+Hc]=F();var O=r.createElement(kb);O.src=N;var P=r.getElementsByTagName(pb)[X];P.insertBefore(O,P.firstElementChild||P.children[X]);return false}}}I();t(Q,Bc);C(J);return true}
mycardgames.succeeded=mycardgames();