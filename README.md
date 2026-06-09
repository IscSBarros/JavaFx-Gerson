# Calculadora 3D - JavaFX

Calculadora de custos para impressão 3D desenvolvida em JavaFX.

## Pré-requisitos

Certifique-se de ter instalado em sua máquina:
- **Java 17** (JDK 17)
- **Maven** (Apache Maven)

Verifique se as variáveis de ambiente `JAVA_HOME` estão configuradas corretamente apontando para o JDK 17.

> [!IMPORTANT]
> Se o seu terminal (como o do VS Code) acusar o erro *"No compiler is provided in this environment"*, significa que ele não sabe onde o seu JDK está instalado. Antes de compilar, rode o comando abaixo no PowerShell para avisá-lo:
> ```powershell
> $env:JAVA_HOME="C:\Program Files\Java\jdk-17"
> ```

## Como Compilar e Rodar o Projeto

Este projeto utiliza o `javafx-maven-plugin` para gerenciar as dependências do JavaFX automaticamente. Não é necessário baixar o SDK do JavaFX separadamente.

Abra o terminal na pasta raiz do projeto e execute os comandos abaixo.

### 1. Limpar e Compilar

Para limpar builds anteriores e compilar o código fonte:
```bash
.\mvnw clean compile
```

### 2. Executar a Aplicação

Para rodar a aplicação diretamente via Maven:
```bash
.\mvnw javafx:run
```

### 3. Gerar um Executável Independente (Não precisa de Java instalado)

Para empacotar a aplicação em um executável autônomo usando `jlink` (ideal para distribuir para outras pessoas):
```bash
.\mvnw clean javafx:jlink
```
Isso vai criar uma pasta chamada `image` dentro da pasta `target` (`target/image`). 
Nessa pasta haverá tudo que o seu programa precisa para rodar! Basta ir na pasta `target/image/bin` e executar o arquivo `calculadora.bat` (ou apenas `calculadora` no Linux/Mac).

---

*Nota:* Na primeira vez que você rodar o comando, o Maven fará o download de todas as dependências do JavaFX necessárias, o que pode levar alguns minutos dependendo da sua internet. Nas execuções seguintes, o processo será quase instantâneo.
