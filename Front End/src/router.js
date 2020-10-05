import Vue from "vue";
import Router from "vue-router";
import store from "./store.js"

/*Layout*/
import MainNavbar from "./layout/MainNavbar.vue";
import MainFooter from "./layout/MainFooter.vue";

/*Views*/
import Index from "./views/Index.vue";
import Login from "./views/Login.vue";
import UserList from "./views/UserList.vue";
import Profile from "./views/Profile.vue";
import Recommend from "./views/Recommend.vue";
import About from "./views/About.vue"
import FAQ from "./views/Faq.vue";
import Register from "./views/Register.vue"
import Forums from "./views/Forums.vue"
import Events from "./views/Events.vue"

Vue.use(Router);

export default new Router({name: 'User',
  routes: [
    {
      path: "/",
      name: "index",
      components: {
        default: Index,
        header: MainNavbar,
        footer: MainFooter
      },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" }
      }
    },
    {
      path: "/login",
      name: "login",
      
      components: {
        default: Login,
        header: MainNavbar,
        footer: MainFooter
      },
      props: {
        header: { colorOnScroll: 400 },
        footer: {backgroundColor: "black"}
      }
    },
    {
      path: "/profile",
      name: "profile",
      
      components: {
        Secure,
        meta: {
          requiresauth: true
        },
        default: Profile,
        header: MainNavbar,
        footer: MainFooter
      },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" }
      }
    },
    {
      path: "/faq",
      name: "faq",

      components: {
        default: FAQ,
        header: MainNavbar,
        footer: MainFooter
      },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" }
      }
    },
    {
      path: "/about",
      name: "about",
      components: {
        default: About,
        header: MainNavbar,
        footer: MainFooter
      },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" }
      }
    },
    {
      path: "/recommendations",
      name: "recommendations",
      
      components: {
        default: Recommend,
        header: MainNavbar,
        footer: MainFooter
      },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black"}
      }
    },
    {
      path: "/forums",
      name: "forums",
      components: {
        default: Forums,
        header: MainNavbar,
        footer: MainFooter
      },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" }
      }
    },
    {
      path: "/userlist",
      name: "userlist",
      components: {
        Secure,
        meta: {
          requiresAuth: true
        },
        default: UserList,
        header: MainNavbar,
        footer: MainFooter
      },
      props: {
        header: {
          colorOnScroll: 400
        },
        footer: {
          backgroundColor: "black"
        }
      }
    },
{
      path: "/register",
      name: "register",
      components: {
        default: Register,
        header: MainNavbar,
        footer: MainFooter
      },
      props: {
        header: {
          colorOnScroll: 400
        },
        footer: {
          backgroundColor: "black"
        }
      }
    },
    {
      path: "/events",
      name: "events",
      components: {
        default: Events,
        header: MainNavbar,
        footer: MainFooter
      }
    },
  ],
  scrollBehavior: to => {
    if (to.hash) {
      return { selector: to.hash };
    } else {
      return { x: 0, y: 0 };
    }
  }


});
router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (store.getters.isLoggedIn) {
      next()
      return
    }
    next('/login')
  } else {
    next()
  }
})
