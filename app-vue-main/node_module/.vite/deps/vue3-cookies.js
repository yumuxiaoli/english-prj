import "./chunk-EVS5FBPM.js";
import {
  reactive
} from "./chunk-PA25SUH2.js";
import "./chunk-IGMYUX52.js";

// node_modules/vue3-cookies/dist/index.js
var defaultConfig = {
  expireTimes: "1d",
  path: "; path=/",
  domain: "",
  secure: false,
  sameSite: "; SameSite=Lax"
};
var VueCookiesManager = function() {
  function VueCookiesManager2() {
    this.current_default_config = defaultConfig;
  }
  VueCookiesManager2.prototype.config = function(config) {
    for (var propertyName in this.current_default_config) {
      this.current_default_config[propertyName] = config[propertyName] ? config[propertyName] : defaultConfig[propertyName];
    }
  };
  VueCookiesManager2.prototype.get = function(keyName) {
    var value = decodeURIComponent(document.cookie.replace(new RegExp("(?:(?:^|.*;)\\s*" + encodeURIComponent(keyName).replace(/[\-\.\+\*]/g, "\\$&") + "\\s*\\=\\s*([^;]*).*$)|^.*$"), "$1")) || null;
    if (value && value.substring(0, 1) === "{" && value.substring(value.length - 1, value.length) === "}") {
      try {
        value = JSON.parse(value);
      } catch (e) {
        return value;
      }
    }
    return value;
  };
  VueCookiesManager2.prototype.set = function(keyName, value, expireTimes, path, domain, secure, sameSite) {
    if (!keyName) {
      throw new Error("Cookie name is not found in the first argument.");
    } else if (/^(?:expires|max-age|path|domain|secure|SameSite)$/i.test(keyName)) {
      throw new Error('Cookie name illegality. Cannot be set to ["expires","max-age","path","domain","secure","SameSite"]	 current key name: ' + keyName);
    }
    if (value && value.constructor === Object) {
      value = JSON.stringify(value);
    }
    var _expires = "";
    if (expireTimes == void 0) {
      expireTimes = this.current_default_config.expireTimes ? this.current_default_config.expireTimes : "";
    }
    if (expireTimes && expireTimes != 0) {
      switch (expireTimes.constructor) {
        case Number:
          if (expireTimes === Infinity || expireTimes === -1)
            _expires = "; expires=Fri, 31 Dec 9999 23:59:59 GMT";
          else
            _expires = "; max-age=" + expireTimes;
          break;
        case String:
          if (/^(?:\d+(y|m|d|h|min|s))$/i.test(expireTimes)) {
            var _expireTime = expireTimes.replace(/^(\d+)(?:y|m|d|h|min|s)$/i, "$1");
            switch (expireTimes.replace(/^(?:\d+)(y|m|d|h|min|s)$/i, "$1").toLowerCase()) {
              case "m":
                _expires = "; max-age=" + +_expireTime * 2592e3;
                break;
              case "d":
                _expires = "; max-age=" + +_expireTime * 86400;
                break;
              case "h":
                _expires = "; max-age=" + +_expireTime * 3600;
                break;
              case "min":
                _expires = "; max-age=" + +_expireTime * 60;
                break;
              case "s":
                _expires = "; max-age=" + _expireTime;
                break;
              case "y":
                _expires = "; max-age=" + +_expireTime * 31104e3;
                break;
              default:
                new Error('unknown exception of "set operation"');
            }
          } else {
            _expires = "; expires=" + expireTimes;
          }
          break;
        case Date:
          _expires = "; expires=" + expireTimes.toUTCString();
          break;
      }
    }
    document.cookie = encodeURIComponent(keyName) + "=" + encodeURIComponent(value) + _expires + (domain ? "; domain=" + domain : this.current_default_config.domain ? this.current_default_config.domain : "") + (path ? "; path=" + path : this.current_default_config.path ? this.current_default_config.path : "; path=/") + (secure == void 0 ? this.current_default_config.secure ? "; Secure" : "" : secure ? "; Secure" : "") + (sameSite == void 0 ? this.current_default_config.sameSite ? "; SameSute=" + this.current_default_config.sameSite : "" : sameSite ? "; SameSite=" + sameSite : "");
    return this;
  };
  VueCookiesManager2.prototype.remove = function(keyName, path, domain) {
    if (!keyName || !this.isKey(keyName)) {
      return false;
    }
    document.cookie = encodeURIComponent(keyName) + "=; expires=Thu, 01 Jan 1970 00:00:00 GMT" + (domain ? "; domain=" + domain : this.current_default_config.domain ? this.current_default_config.domain : "") + (path ? "; path=" + path : this.current_default_config.path ? this.current_default_config.path : "; path=/") + "; SameSite=Lax";
    return true;
  };
  VueCookiesManager2.prototype.isKey = function(keyName) {
    return new RegExp("(?:^|;\\s*)" + encodeURIComponent(keyName).replace(/[\-\.\+\*]/g, "\\$&") + "\\s*\\=").test(document.cookie);
  };
  VueCookiesManager2.prototype.keys = function() {
    if (!document.cookie)
      return [];
    var _keys = document.cookie.replace(/((?:^|\s*;)[^\=]+)(?=;|$)|^\s*|\s*(?:\=[^;]*)?(?:\1|$)/g, "").split(/\s*(?:\=[^;]*)?;\s*/);
    for (var _index = 0; _index < _keys.length; _index++) {
      _keys[_index] = decodeURIComponent(_keys[_index]);
    }
    return _keys;
  };
  return VueCookiesManager2;
}();
var dist_default = {
  install: function(app, options) {
    app.config.globalProperties.$cookies = new VueCookiesManager();
    if (options) {
      app.config.globalProperties.$cookies.config(options);
    }
  }
};
var GLOBAL_COOKIES_MANAGER = null;
function globalCookiesConfig(options) {
  if (GLOBAL_COOKIES_MANAGER == null) {
    GLOBAL_COOKIES_MANAGER = new VueCookiesManager();
  }
  GLOBAL_COOKIES_MANAGER.config(options);
}
function useCookies() {
  if (GLOBAL_COOKIES_MANAGER == null) {
    GLOBAL_COOKIES_MANAGER = new VueCookiesManager();
  }
  var cookies = reactive(GLOBAL_COOKIES_MANAGER);
  return { cookies };
}

// dep:vue3-cookies
var vue3_cookies_default = dist_default;
export {
  vue3_cookies_default as default,
  globalCookiesConfig,
  useCookies
};
//# sourceMappingURL=vue3-cookies.js.map
