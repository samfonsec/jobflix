## Jobflix

**Jobflix** é um aplicativo moderno de listagem de séries de TV. Com ele, é possível explorar séries populares, buscar por título ou por atores e atrizes, visualizar detalhes completos das séries e ainda manter uma lista de favoritas. O app também conta com um sistema de segurança por PIN ou biometria.

---

### Tecnologias e Frameworks Utilizados

- **Kotlin**
- **Arquitetura MVVM**
- **Coroutines**para chamadas assíncronas e concorrência eficiente
- **Koin**
- **Room / Retrofit**
- **BiometricPrompt**
- **LiveData**
- **Material Design Components**

---

### Funcionalidades

- **Busca de Séries**
- **Detalhes da Série**
- **Busca por Atores/Atrizes**
- **Favoritos**
- **Sistema de Segurança**
  - Acesso por PIN ou autenticação biométrica.

---

### Organização do Projeto

br.com.jobflix/
- **data**: Fontes de dados (APIs, DAOs, Repositórios)
- **di**: Módulos de injeção de dependência (Koin)
- **ui**: Activities, Fragments, ViewModels
  - **auth**: Lógica e telas de autenticação por PIN ou biometria
- **utils**: Classes utilitárias e helpers

---

### Incompleto / A melhorar
- Modo escuro
- Suporte a múltiplos idiomas
- Utilizar **Navigation Component** e **Jetpack Compose**
- Utilizar mais testes unitários e de UI
- Rever alguns métodos e regras de negócio

--- 

### Screenshots

<img src="https://github.com/user-attachments/assets/2e77e8c7-257d-49de-8b40-207ecb658a98" width="300" height="570"/>
<img src="https://github.com/user-attachments/assets/712013a0-44f7-4a75-9558-951c21594712" width="300" height="570"/>
<img src="https://github.com/user-attachments/assets/6fec4a14-f3ce-4344-bfcc-58b98e3c002b" width="300" height="570"/>
<img src="https://github.com/user-attachments/assets/1bb6dfaa-04f3-4085-aa80-b8728b7e8738" width="300" height="570"/>
<img src="https://github.com/user-attachments/assets/61533a5a-97e9-49a6-a72e-7436174c4880" width="300" height="570"/>
<img src="https://github.com/user-attachments/assets/f8554cba-310c-4aee-b91b-9c5f41b787c8" width="300" height="570"/>


Desenvolvido por Samuel Fonseca
