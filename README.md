# IntegrityVault: Verificação de Integridade Documental com Blockchain 🛡️🎓

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Solidity](https://img.shields.io/badge/Solidity-363636?style=for-the-badge&logo=solidity&logoColor=white)
![Polygon](https://img.shields.io/badge/Polygon-8247E5?style=for-the-badge&logo=polygon&logoColor=white)

## 📌 Sobre o Projeto
O **IntegrityVault** é uma solução descentralizada desenvolvida como Trabalho de Conclusão de Curso (TCC) para o IF Sertão-PE. O objetivo é combater a fraude em documentos acadêmicos (diplomas e certificados) através do registro de hashes criptográficos na rede **Polygon**.

A aplicação permite que instituições registrem a "impressão digital" (SHA-256) de documentos na blockchain, garantindo imutabilidade e auditabilidade pública sem expor dados sensíveis dos alunos, em conformidade com a LGPD.

## 🚀 Arquitetura do Sistema
O projeto segue uma arquitetura de monorepo dividida em:
- **`/blockchain`**: Smart Contracts em Solidity e scripts de deploy via Hardhat.
- **`/backend`**: API REST robusta desenvolvida em Spring Boot (Java 17) seguindo os princípios de Clean Architecture.



## 🛠️ Tech Stack
- **Smart Contracts:** Solidity.
- **Ferramentas Blockchain:** Hardhat, Ethers.js, MetaMask.
- **Rede:** Polygon (Amoy Testnet).
- **Backend:** Java 17, Spring Boot 3, Web3j (Integração Blockchain).
- **Banco de Dados:** MySQL (Persistência off-chain de metadados).
- **Segurança:** JWT para autenticação institucional.

## ⚙️ Principais Funcionalidades
- **Registro de Hash:** Geração de SHA-256 de arquivos PDF e envio para a rede Polygon.
- **Verificação Pública:** Consulta gratuita on-chain para validar se um documento foi alterado.
- **Gestão de Emissores:** Apenas endereços autorizados (Owner) podem registrar novos documentos.
- **Processamento Assíncrono:** Integração com Web3j utilizando `CompletableFuture` para lidar com o tempo de confirmação de blocos.

## 🔧 Como Executar

### Pré-requisitos
- Java 17+
- Node.js & npm
- MySQL 8
- Web3j CLI

### 1. Blockchain (Hardhat)
```bash
cd blockchain
npm install
# Configure o seu .env com RPC_URL e PRIVATE_KEY
npx hardhat run scripts/deploy.ts --network amoy
```
### 2. Backend (Spring Boot)
```bash
cd backend
# Gere o wrapper do contrato antes de rodar
mvn compile
mvn spring-boot:run
```
📄 Licença
Este projeto está sob a licença MIT
--
Desenvolvido por Gabriel Leal _Estudante de Sistemas para Internet - IF Sertão-PE_
