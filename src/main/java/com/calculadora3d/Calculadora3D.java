package com.calculadora3d;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Calculadora3D extends Application {
    // Dados da impressora
    private final String NOME_IMPRESSORA = "Impressora Exemplo";
    private final double PRECO_MAQUINA = 1500.0;
    private final double POTENCIA_W = 350.0;
    private final double VIDA_UTIL_H = 2920.0;

    // Dados dos materiais (Nome e preço por grama)
    private final String[] LISTA_MATERIAIS = { "PLA (0,12 R$/g)", "ABS (0,14 R$/g)", "PETG (0,16 R$/g)" };
    private final double[] PRECO_GRAMA = { 0.12, 0.14, 0.16 };

    // --- Componentes da Interface ---
    private ComboBox<String> comboBoxImpressora;
    private TextField campoDescricaoObjeto;

    private ComboBox<String> comboBoxMaterial, comboBoxDensidade, comboBoxQualidade;
    private TextField campoPesoModelo, campoPesoSuporte, campoTaxaFalha, campoQuantidadePecas;
    private TextField campoTempoImpressao, campoTempoPreparo;

    private RadioButton radioTarifaDiurna, radioTarifaNoturna;
    private TextField campoValorMaoDeObra, campoMargemLucro;

    // Labels para mostrar os resultados dos cálculos
    private Label labelCustoMaterial, labelCustoMaquina, labelCustoEnergia;
    private Label labelCustoManutencao, labelCustoMaoDeObra;
    private Label labelCustoTotal, labelValorVenda;

    @Override
    public void start(Stage stage) {
        // --- Cabeçalho ---
        Label titulo = new Label("Calculadora de Custos");
        titulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        titulo.setStyle("-fx-text-fill: white;");
        HBox header = new HBox(titulo);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #2c3e50;"); // Azul escuro elegante

        // --- Seção 1: Impressora ---
        comboBoxImpressora = new ComboBox<>(FXCollections.observableArrayList(NOME_IMPRESSORA));
        comboBoxImpressora.setValue(NOME_IMPRESSORA);
        comboBoxImpressora.setPrefWidth(250);

        ImageView imagemImpressora = new ImageView(
                new Image(getClass().getResourceAsStream("/com/calculadora3d/images/ender3.png")));
        imagemImpressora.setFitWidth(160);
        imagemImpressora.setPreserveRatio(true);

        VBox infoImpressora = new VBox(8,
                criarLabelNegrito("Preço de Aquisição: R$ 1.500,00"),
                criarLabelNegrito("Potência: 350 W"),
                new Label("Descrição: Impressora FDM de entrada,\nideal para protótipos e peças finais."));
        infoImpressora.setStyle(
                "-fx-background-color: #f8f9fa; -fx-padding: 15; -fx-border-color: #dee2e6; -fx-border-radius: 5; -fx-background-radius: 5;");

        HBox secaoImpressora = new HBox(30,
                new VBox(10, new Label("Escolha a Impressora:"), comboBoxImpressora, infoImpressora), imagemImpressora);
        VBox cardImpressora = criarCard("1. Impressora", secaoImpressora);

        // --- Seção 2: Dados do Projeto ---
        campoDescricaoObjeto = criarTextField("Ex: Suporte para celular", 300);

        comboBoxMaterial = new ComboBox<>(FXCollections.observableArrayList(LISTA_MATERIAIS));
        comboBoxMaterial.setValue(LISTA_MATERIAIS[0]);
        comboBoxDensidade = new ComboBox<>(
                FXCollections.observableArrayList("Baixa (Oco)", "Média (Padrão)", "Alta (Maciço)"));
        comboBoxDensidade.setValue("Média (Padrão)");
        comboBoxDensidade.setPrefWidth(140);
        comboBoxQualidade = new ComboBox<>(
                FXCollections.observableArrayList("Rascunho (0.28mm)", "Normal (0.2mm)", "Fina (0.12mm)"));
        comboBoxQualidade.setValue("Normal (0.2mm)");
        comboBoxQualidade.setPrefWidth(140);

        campoPesoModelo = criarTextField("120", 70);
        campoPesoSuporte = criarTextField("20", 70);
        campoTaxaFalha = criarTextField("10", 70);
        campoQuantidadePecas = criarTextField("1", 70);
        campoTempoImpressao = criarTextField("5", 70);
        campoTempoPreparo = criarTextField("1", 70);

        radioTarifaDiurna = new RadioButton("Diurna (R$ 0,95 / kWh)");
        radioTarifaNoturna = new RadioButton("Noturna (R$ 0,65 / kWh)");
        ToggleGroup grupoTarifa = new ToggleGroup();
        radioTarifaDiurna.setToggleGroup(grupoTarifa);
        radioTarifaNoturna.setToggleGroup(grupoTarifa);
        radioTarifaDiurna.setSelected(true);
        HBox caixaTarifa = new HBox(15, radioTarifaDiurna, radioTarifaNoturna);

        campoValorMaoDeObra = criarTextField("20.00", 70);
        campoMargemLucro = criarTextField("30", 70);

        GridPane gridDados = new GridPane();
        gridDados.setHgap(15);
        gridDados.setVgap(15);
        gridDados.addRow(0, new Label("Descrição do Objeto:"), campoDescricaoObjeto);
        gridDados.addRow(1, new Label("Material:"), comboBoxMaterial, new Label("Densidade:"), comboBoxDensidade,
                new Label("Qualidade:"), comboBoxQualidade);
        gridDados.addRow(2, new Label("Peso Modelo (g):"), campoPesoModelo, new Label("Peso Suporte (g):"),
                campoPesoSuporte, new Label("Taxa de Falha (%):"), campoTaxaFalha);
        gridDados.addRow(3, new Label("Qtd. de Peças:"), campoQuantidadePecas, new Label("Tempo Impressão (h):"),
                campoTempoImpressao, new Label("Tempo Preparo (h):"), campoTempoPreparo);
        gridDados.addRow(4, new Label("Tarifa de Energia:"), caixaTarifa);
        gridDados.addRow(5, new Label("Mão de Obra (R$/h):"), campoValorMaoDeObra, new Label("Margem de Lucro (%):"),
                campoMargemLucro);

        Button botaoCalcular = new Button("Calcular Custos");
        botaoCalcular.setStyle(
                "-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 30; -fx-cursor: hand;");
        botaoCalcular.setOnAction(e -> calcularCustos());

        VBox cardDados = criarCard("2. Dados do Projeto", new VBox(20, gridDados, botaoCalcular));

        // --- Seção 3: Resultados ---
        labelCustoMaterial = criarLabelValor();
        labelCustoMaquina = criarLabelValor();
        labelCustoEnergia = criarLabelValor();
        labelCustoManutencao = criarLabelValor();
        labelCustoMaoDeObra = criarLabelValor();
        labelCustoTotal = criarLabelValorDestacado("#27ae60"); // Cor verde
        labelValorVenda = criarLabelValorDestacado("#8e44ad"); // Cor roxa

        GridPane gridResultados = new GridPane();
        gridResultados.setHgap(30);
        gridResultados.setVgap(12);

        // Define as colunas do Grid para garantir que o texto não seja cortado
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(170);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPrefWidth(120);
        gridResultados.getColumnConstraints().addAll(col1, col2);

        gridResultados.addRow(0, criarLabelNegrito("Custo com Material:"), labelCustoMaterial);
        gridResultados.addRow(1, criarLabelNegrito("Depreciação da Máquina:"), labelCustoMaquina);
        gridResultados.addRow(2, criarLabelNegrito("Custo de Energia:"), labelCustoEnergia);
        gridResultados.addRow(3, criarLabelNegrito("Manutenção Preventiva:"), labelCustoManutencao);
        gridResultados.addRow(4, criarLabelNegrito("Custo de Mão de Obra:"), labelCustoMaoDeObra);
        gridResultados.add(new Separator(), 0, 5, 2, 1);
        gridResultados.addRow(6, criarLabelNegrito("CUSTO TOTAL:"), labelCustoTotal);
        gridResultados.addRow(7, criarLabelNegrito("VALOR DE VENDA:"), labelValorVenda);

        VBox cardResultados = criarCard("3. Resumo de Custos", gridResultados);

        // --- Montagem do Layout Principal ---
        VBox colunaEsquerda = new VBox(20, cardImpressora, cardDados);
        VBox colunaDireita = new VBox(20, cardResultados);

        HBox conteudoPrincipal = new HBox(20, colunaEsquerda, colunaDireita);
        conteudoPrincipal.setPadding(new Insets(20));
        conteudoPrincipal.setStyle("-fx-background-color: #ecf0f1;"); // Fundo cinza suave

        ScrollPane rolagem = new ScrollPane(conteudoPrincipal);
        rolagem.setFitToWidth(true);
        rolagem.setStyle("-fx-background: #ecf0f1;");

        VBox raiz = new VBox(header, rolagem);
        // Aumentando tamanho da tela para 1250x750 para acomodar bem tudo
        Scene cena = new Scene(raiz, 1250, 750);
        stage.setScene(cena);
        stage.setTitle("Calculadora de Custos de Impressão 3D");
        stage.show();
    }

    // --- Lógica de Cálculo ---
    private void calcularCustos() {
        try {
            // Obter preço por grama baseado no material selecionado
            int indiceMaterial = comboBoxMaterial.getSelectionModel().getSelectedIndex();
            double precoGrama = PRECO_GRAMA[indiceMaterial];

            // Fatores de Densidade e Qualidade
            double multDensidade = 1.0;
            switch (comboBoxDensidade.getValue()) {
                case "Baixa (Oco)":
                    multDensidade = 0.8;
                    break;
                case "Alta (Maciço)":
                    multDensidade = 1.2;
                    break;
            }

            double multTempo = 1.0;
            switch (comboBoxQualidade.getValue()) {
                case "Rascunho (0.28mm)":
                    multTempo = 0.7;
                    break;
                case "Fina (0.12mm)":
                    multTempo = 1.5;
                    break;
            }

            // Ler os valores digitados nos campos
            double peso = lerNumero(campoPesoModelo) * multDensidade; // Densidade afeta o peso
            double suporte = lerNumero(campoPesoSuporte);
            double taxaFalha = lerNumero(campoTaxaFalha) / 100.0;
            double quantidade = lerNumero(campoQuantidadePecas);

            double tempoImpressao = lerNumero(campoTempoImpressao) * multTempo; // Qualidade afeta o tempo
            double tempoPreparo = lerNumero(campoTempoPreparo);

            double tarifaEnergia = radioTarifaDiurna.isSelected() ? 0.95 : 0.65;
            double valorHoraMaoObra = lerNumero(campoValorMaoDeObra);
            double margemLucro = lerNumero(campoMargemLucro) / 100.0;

            // Realizar os cálculos matemáticos para UMA peça
            double pesoTotalPorPeca = (peso + suporte) * (1.0 + taxaFalha);

            double custoMat = pesoTotalPorPeca * precoGrama;
            double custoMaq = (PRECO_MAQUINA / VIDA_UTIL_H) * tempoImpressao;
            double custoEne = (POTENCIA_W / 1000.0) * tempoImpressao * tarifaEnergia;
            double custoMan = 0.50 * tempoImpressao; // R$ 0,50 por hora
            double custoMao = (tempoImpressao + tempoPreparo) * valorHoraMaoObra;

            double custoTotalPeca = custoMat + custoMaq + custoEne + custoMan + custoMao;
            double vendaSugeridaPeca = custoTotalPeca * (1.0 + margemLucro);

            // Multiplicar pela quantidade de peças
            custoMat *= quantidade;
            custoMaq *= quantidade;
            custoEne *= quantidade;
            custoMan *= quantidade;
            custoMao *= quantidade;
            custoTotalPeca *= quantidade;
            vendaSugeridaPeca *= quantidade;

            // Atualizar os textos na interface formatados como Moeda (R$)
            labelCustoMaterial.setText(formatarMoeda(custoMat));
            labelCustoMaquina.setText(formatarMoeda(custoMaq));
            labelCustoEnergia.setText(formatarMoeda(custoEne));
            labelCustoManutencao.setText(formatarMoeda(custoMan));
            labelCustoMaoDeObra.setText(formatarMoeda(custoMao));
            labelCustoTotal.setText(formatarMoeda(custoTotalPeca));
            labelValorVenda.setText(formatarMoeda(vendaSugeridaPeca));

        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, "Por favor, digite apenas números válidos!").show();
        }
    }

    // --- Métodos Utilitários (Helpers) para deixar o código limpo ---
    private TextField criarTextField(String textoPadrao, int largura) {
        TextField campo = new TextField(textoPadrao);
        campo.setPrefWidth(largura);
        return campo;
    }

    private Label criarLabelValor() {
        Label label = new Label("R$ 0,00");
        label.setFont(Font.font("Arial", 14));
        label.setMinWidth(100); // Garante que o texto não seja cortado
        return label;
    }

    private Label criarLabelValorDestacado(String corHex) {
        Label label = new Label("R$ 0,00");
        label.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: " + corHex + ";");
        label.setMinWidth(120); // Garante que o texto não seja cortado
        return label;
    }

    private Label criarLabelNegrito(String texto) {
        Label label = new Label(texto);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        return label;
    }

    private VBox criarCard(String titulo, javafx.scene.Node conteudo) {
        Label labelTitulo = new Label(titulo);
        labelTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        labelTitulo.setStyle("-fx-text-fill: #2980b9;"); // Azul claro

        VBox card = new VBox(15, labelTitulo, new Separator(), conteudo);
        card.setStyle(
                "-fx-background-color: white; -fx-padding: 20; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
        return card;
    }

    private double lerNumero(TextField campo) {
        // Substitui vírgula por ponto para evitar erro de formatação
        return Double.parseDouble(campo.getText().replace(",", "."));
    }

    private String formatarMoeda(double valor) {
        return String.format("R$ %,.2f", valor);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
