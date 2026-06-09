# Calculadora 3D - Custos de Impressão 3D

## O que é?
Uma aplicação JavaFX que calcula os custos de impressão 3D, permitindo ao usuário
selecionar uma impressora, definir parâmetros do projeto e obter um detalhamento
completo dos custos envolvidos.

---

## Estrutura do Projeto

```
JavaFX Gerson/
├── pom.xml                          → Arquivo Maven (gerencia dependências JavaFX)
├── src/main/java/
│   ├── module-info.java             → Declara o módulo Java (exigido pelo JavaFX 17+)
│   └── com/calculadora3d/
│       └── Calculadora3D.java       → ARQUIVO PRINCIPAL (toda a aplicação)
└── src/main/resources/
    └── com/calculadora3d/images/
        ├── ender3.png               → Imagem da impressora Creality Ender 3
        └── prusa.png                → Imagem da impressora Prusa i3 MK3S+
```

---

## Explicação do Código (Calculadora3D.java)

### Imports (linhas 1-14)
Importações das classes JavaFX necessárias:
- `Application` → Classe base para apps JavaFX
- `ComboBox, TextField, Slider, Button, Label` → Componentes de entrada/saída
- `HBox, VBox, GridPane, ScrollPane` → Containers de layout
- `ImageView, Image` → Para exibir imagens das impressoras

### Dados das Impressoras (linhas 24-36)
Arrays com dados fixos de 2 impressoras:
- `IMP[][]` → Nome, preço, potência (W), vida útil (horas), descrição, dimensões
- `IMG[]` → Caminhos das imagens nos resources
- `MATERIAIS[]` e `PRECO_G[]` → Tipos de filamento e preço por grama

### Declaração dos Componentes (linhas 38-49)
Todas as variáveis dos componentes visuais que precisam ser acessados
depois da criação (para ler valores ou atualizar textos).

### Método start() (linhas 51-140)
Método principal que monta toda a interface. Dividido em seções:

#### Header (linhas 53-57)
- `HBox` com fundo azul escuro e o título da aplicação em branco

#### Seção 1 - Escolha da Impressora (linhas 59-76)
- `ComboBox` para selecionar entre as 2 impressoras
- `ImageView` que mostra a foto da impressora selecionada
- Labels mostrando preço, potência, descrição e dimensões
- Ao trocar a impressora no ComboBox, os dados e a imagem atualizam

#### Seção 2 - Dados do Projeto (linhas 78-107)
Todos os campos de entrada organizados em linhas com `HBox`:
- `TextField` para arquivo STL, descrição, peso, suporte, falha, quantidade
- `ComboBox` para material, densidade e qualidade
- `RadioButton` para tarifa diurna/noturna (com `ToggleGroup`)
- `TextField` para mão de obra e margem de lucro
- Botões "Calcular Custos" e "Limpar"

#### Seção 3 - Resumo de Custos (linhas 109-126)
- `GridPane` funcionando como tabela com 3 colunas: Item, Detalhamento, Custo
- 5 linhas de custo: Material, Máquina, Energia, Manutenção, Mão de Obra
- Cada item com cor diferente (●) para fácil identificação
- Linha de CUSTO TOTAL e Valor de venda sugerido

#### Seção 5 - Resumo do Projeto (linhas 128-135)
- Lista de `Label` mostrando dados resumidos: impressora, material, peso, tempo, custo, venda, lucro

#### Layout Principal (linhas 137-155)
- `HBox` com painel esquerdo (seções 1+2) e direito (seções 3+5)
- `ScrollPane` para rolar o conteúdo
- Footer com botões extras (Comparar, Salvar, Histórico, Exportar)

### Método calcular() (linhas 162-188)
As fórmulas de cálculo dos custos:

| Custo | Fórmula |
|-------|---------|
| **Material** | (peso + suporte) × (1 + taxaFalha) × preçoPorGrama |
| **Energia** | (potência/1000) × tempoImpressão × tarifaEnergia |
| **Máquina** | (preçoAquisição / vidaÚtil) × tempoImpressão |
| **Manutenção** | R$ 0,50/hora × tempoImpressão |
| **Mão de Obra** | (tempoImpressão + tempoPreparação) × valorHora |
| **Total** | Soma de todos os custos |
| **Venda** | custoTotal × (1 + margem/100) |

### Métodos Helpers (linhas 197-215)
- `f(double)` → Formata valor como moeda brasileira (R$ 1.234,56)
- `d(String)` → Converte texto para double (aceita vírgula ou ponto)
- `secao()` → Cria um card branco com título e borda
- `rotVal()` → Cria uma linha "Rótulo: Campo" alinhada
- `bold()` / `cor()` → Criam labels com estilo específico

---

## Como Executar

1. **Pré-requisito:** JDK 17 instalado
2. No terminal, dentro da pasta do projeto:
```bash
set JAVA_HOME=C:\Program Files\Java\jdk-17
.\apache-maven-3.9.16\bin\mvn.cmd clean javafx:run
```

---

## Componentes JavaFX Utilizados

| Componente | Para quê |
|------------|----------|
| `ComboBox` | Dropdown de seleção (impressora, material, qualidade) |
| `TextField` | Entrada de texto/números (peso, tempo, etc.) |
| `RadioButton` | Seleção exclusiva (tarifa diurna/noturna) |
| `Button` | Ações (Calcular, Limpar) |
| `Label` | Exibir textos e resultados |
| `ImageView` | Exibir imagem da impressora |
| `Slider` | Não usado nesta versão (substituído por TextField) |
| `HBox` | Layout horizontal (itens lado a lado) |
| `VBox` | Layout vertical (itens empilhados) |
| `GridPane` | Tabela de custos (linhas e colunas) |
| `ScrollPane` | Scroll quando conteúdo é maior que a janela |
| `Separator` | Linha divisória visual |
