const Home = {
  text: $localize `Tableau de bord`,
  link: '/home',
  icon: 'fas fa-align-center',
  roles: ['*'],
  module: ['*']
};


const Stock = {
  text: $localize `Stock`,
  link: '/stock',
  icon: 'fa-dolly-flatbed fas',
  submenu: [
    {
      text: $localize `Articles`,
      link: '/stock/articles',
      roles: ['*'],
    }
  ],
  roles: ['ST_R', 'ST_W', 'ST_A', 'ST_S', 'ST_Q','GL_A','ERP_A'],
  module: ['*']
};

const Document = {
  text:  `Gestion de documentaire`,
  link: '/documentary',
  icon: 'fa-folder-open fas',
};


const Project = {
  text: $localize`Projet/Affaires`,
  link: '/project',
  icon: 'fa-cube fas',
  submenu: [
    {
      text: $localize`Listes des projets/affaires`,
      link: '/project/list',
      roles: ['*'],
    }, {
      text: $localize`Taches`,
      link: '/project/tasks',
      roles: ['*'],
    }
  ],
  roles: ['PJ_R', 'PJ_W', 'PJ_A', 'PJ_S','GL_A','ERP_A'],
  module: ['*']
};

const MES = {
  text:  `Manufacturing`,
  heading: true,
};

const GPAO = {
  text: $localize `GPAO`,
  link: '/mes',
  icon: 'icon-puzzle',
  submenu: [
    {
      text: $localize`Plannig de production`,
      link: '/mes/planning',
      roles: ['*']
    }, {
      text: $localize`Pointage de production`,
      link: '/mes/pilotage-production',
      roles: ['*']
    }, {
      text: $localize`Ordre de fabrication (OF)`,
      link: '/mes/po',
      roles: ['*']
    }, {
      text: $localize`Tableau de bord`,
      link: '/mes/dashboard',
      roles: ['*']
    }, {
      text: $localize`Paramètres`,
      link: '/mes/settings',
      roles: ['PJ_W', 'PJ_A','PR_M_R', 'PR_M_W', 'PR_M_A', 'PR_A_R', 'PR_A_W', 'PR_A_A','GL_A','ERP_A']
    }
  ],
  roles: ['PR_M_R', 'PR_M_W', 'PR_M_A', 'PR_A_R', 'PR_A_W', 'PR_A_A','GL_A','ERP_A'],
  module:['*']
};

const equipment = {
  text: $localize `Equipement`,
  link: '/equipment/list',
  icon: 'fa-car fas',
  roles: ['GL_A','ERP_A'],
  module:['*']
};

const quality = {
  text: $localize `Qualité`,
  link: '/quality',
  icon: 'fa-heartbeat fas',
  submenu: [
    {
      text: $localize`Anomalies de production`,
      link: '/quality/anomalies',
      roles: ['*'],
    },
    {
      text: $localize`Fiches de non-conformité`,
      link: '/quality/FNC',
      roles: ['*'],
    },
    {
      text: $localize`tableau de bord`,
      link: '/quality/dashboard',
      roles: ['*'],
      module:['*']
    }
  ],
  roles: ['QT_A','QT_R','QT_W','QT_S','GL_A','ERP_A'],
  module:['*']
};




export const menu = [
  Home,
  Stock,
  equipment,
  Project,
  MES,
  GPAO,
  quality,
  Document
];

























