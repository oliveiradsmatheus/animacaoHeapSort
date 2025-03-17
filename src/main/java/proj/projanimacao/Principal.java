package proj.projanimacao;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.util.Random;

import static java.lang.Integer.parseInt;
import static java.lang.Thread.sleep;

public class Principal extends Application {
    AnchorPane pane;
    private Button botaoInicio, botaoTL, botaoTL2, botaoPai, botaoFE, botaoFD, botaoMaior;
    private Button[] vet, arv;
    private Text[] indice, algoritmo;
    private int TL;

    public static void main(String[] args) {
        launch(args);
    }

    private void estilizarBotao(Button botao, String corFundo, String corTexto, String corBorda) {
        String estilo = "-fx-background-color: " + corFundo + "; " +
                "-fx-text-fill: " + corTexto + "; " +
                "-fx-font-size: 14; " +
                "-fx-border-color: " + corBorda + "; " +
                "-fx-border-radius: 5px; " +
                "-fx-border-width: 2px;" +
                "-fx-background-radius: 5px;";
        botao.setStyle(estilo);
    }

    private void gerarVetor() {
        vet = new Button[TL];
        indice = new Text[TL];
        Random aleat = new Random();

        // Primeiro instancia-se os indices para que o vetor sobrepunha-o ao mover.
        for (int i = 0; i < TL; i++) {
            indice[i] = new Text("" + i);
            indice[i].setStyle("-fx-fill: #F8F8F2");
            indice[i].setFont(new Font(14));
            indice[i].setLayoutX(115 + i * 40);
            indice[i].setLayoutY(260);
            pane.getChildren().add(indice[i]);
        }

        for (int i = 0; i < TL; i++) {
            vet[i] = new Button("" + aleat.nextInt(1, 51));
            estilizarBotao(vet[i], "#8BE9FD", "#282A36", "#44475A");
            estilizarBotao(vet[i], "#8BE9FD", "#282A36", "#44475A");
            vet[i].setLayoutX(100 + i * 40);
            vet[i].setLayoutY(200);
            vet[i].setMinHeight(40);
            vet[i].setMinWidth(40);
            pane.getChildren().add(vet[i]);
        }
    }

    private void posicionarArvore(Button[] arv, int inicio, int fim, int nivel, int no) {
        int pos = (inicio + fim) / 2;

        arv[no] = new Button(vet[no].getText());
        arv[no].setLayoutX(pos);
        arv[no].setLayoutY(260 + nivel * 80);
        arv[no].setMinHeight(40);
        arv[no].setMinWidth(40);
        estilizarBotao(arv[no], "#FFB86C", "#282A36", "#44475A");
        pane.getChildren().add(arv[no]);
        if ((no * 2 + 1) < TL)
            posicionarArvore(arv, inicio, pos - 40, nivel + 1, 2 * no + 1);
        if ((no * 2 + 2) < TL)
            posicionarArvore(arv, pos + 40, fim, nivel + 1, 2 * no + 2);
    }

    private void posicionarIndice(int inicio, int fim, int nivel, int no) {
        Text i = new Text("" + no);
        int pos = (inicio + fim) / 2;

        i.setStyle("-fx-fill: #F8F8F2");
        i.setFont(new Font(14));
        i.setLayoutX(pos + 15);
        i.setLayoutY(320 + nivel * 80);
        pane.getChildren().add(i);
        if ((no * 2 + 1) < TL)
            posicionarIndice(inicio, pos - 40, nivel + 1, 2 * no + 1);
        if ((no * 2 + 2) < TL)
            posicionarIndice(pos + 40, fim, nivel + 1, 2 * no + 2);
    }

    private void gerarArvore() {
        arv = new Button[TL];
        posicionarIndice(100, 100 + (TL - 1) * 40, 1, 0);
        posicionarArvore(arv, 100, 100 + (TL - 1) * 40, 1, 0);
    }

    private void gerarBotaoInicio() {
        botaoInicio = new Button("Iniciar Ordenação");
        String estiloPadrao = "-fx-background-color: #44475A; " +
                "-fx-text-fill: #F8F8F2; " +
                "-fx-font-size: 14; " +
                "-fx-border-color: #BD93F9; " +
                "-fx-border-radius: 5px; " +
                "-fx-border-width: 2px;" +
                "-fx-background-radius: 5px;";

        botaoInicio.setStyle(estiloPadrao);
        botaoInicio.setOnMouseEntered(_ -> botaoInicio.setStyle(estiloPadrao.replace("#44475A", "#6272A4")));
        botaoInicio.setOnMousePressed(_ -> {
            botaoInicio.setStyle(estiloPadrao.replace("#44475A", "#282A36")); // Altera a cor de fundo no clique
        });
        botaoInicio.setOnMouseReleased(_ -> botaoInicio.setStyle(estiloPadrao.replace("#44475A", "#6272A4")));
        botaoInicio.setOnMouseExited(_ -> botaoInicio.setStyle(estiloPadrao));
        botaoInicio.setLayoutX(100);
        botaoInicio.setLayoutY(100);
        botaoInicio.setMinHeight(40);
        botaoInicio.setMinWidth(80);
        botaoInicio.setOnAction(_ -> iniciar());
        pane.getChildren().add(botaoInicio);
    }

    private void gerarTL() {
        botaoTL = new Button("TL: " + TL);

        botaoTL.setStyle("-fx-background-color: lightblue;");
        botaoTL.setStyle("-fx-fill: #F8F8F2");
        botaoTL.setLayoutX(100);
        botaoTL.setLayoutY(50);
        botaoTL.setMinHeight(40);
        botaoTL.setMinWidth(80);
        estilizarBotao(botaoTL, "#44475A", "#F8F8F2", "#BD93F9");
        pane.getChildren().add(botaoTL);
    }

    private void gerarTL2() {
        botaoTL2 = new Button("TL2: " + TL);

        botaoTL2.setStyle("-fx-background-color: lightblue;");
        botaoTL2.setStyle("-fx-fill: #F8F8F2");
        botaoTL2.setLayoutX(185);
        botaoTL2.setLayoutY(50);
        botaoTL2.setMinHeight(40);
        botaoTL2.setMinWidth(80);
        estilizarBotao(botaoTL2, "#44475A", "#F8F8F2", "#BD93F9");
        pane.getChildren().add(botaoTL2);
    }

    private void gerarPai() {
        botaoPai = new Button("Pai: ");

        botaoPai.setLayoutX(435);
        botaoPai.setLayoutY(50);
        botaoPai.setMinHeight(40);
        botaoPai.setMinWidth(80);
        estilizarBotao(botaoPai, "#FF5555", "#F8F8F2", "#44475A");
        pane.getChildren().add(botaoPai);
    }

    private void gerarFE() {
        botaoFE = new Button("FE: ");

        botaoFE.setLayoutX(390);
        botaoFE.setLayoutY(100);
        botaoFE.setMinHeight(40);
        botaoFE.setMinWidth(80);
        botaoFE.setFont(new Font(14));
        estilizarBotao(botaoFE, "#BD93F9", "#282A36", "#44475A");
        pane.getChildren().add(botaoFE);
    }

    private void gerarFD() {
        botaoFD = new Button("FD: ");

        botaoFD.setLayoutX(480);
        botaoFD.setLayoutY(100);
        botaoFD.setMinHeight(40);
        botaoFD.setMinWidth(80);
        botaoFD.setFont(new Font(14));
        estilizarBotao(botaoFD, "#FF79C6", "#282A36", "#44475A");
        pane.getChildren().add(botaoFD);
    }

    private void gerarMaior() {
        botaoMaior = new Button("Maior: ");

        botaoMaior.setLayoutX(570);
        botaoMaior.setLayoutY(100);
        botaoMaior.setMinHeight(40);
        botaoMaior.setMinWidth(80);
        botaoMaior.setFont(new Font(14));
        estilizarBotao(botaoMaior, "#50fA7B", "#282A36", "#44475A");
        pane.getChildren().add(botaoMaior);
    }

    private void gerarTitulo() {
        Text nome = new Text("Matheus Oliveira da Silva - 102421080");

        nome.setStyle("-fx-fill: #F8F8F2; -fx-font-weight: bold");
        nome.setFont(new Font(14));
        nome.setLayoutX(230);
        nome.setLayoutY(20);
        pane.getChildren().add(nome);
    }

    private void gerarBotoes() {
        gerarTitulo();
        gerarBotaoInicio();
        gerarTL();
        gerarTL2();
        gerarPai();
        gerarFE();
        gerarFD();
        gerarMaior();
    }

    private void adicionarLinha(String linha, int y, int i) {
        if (i != -1) {
            algoritmo[i] = new Text(linha);
            algoritmo[i].setStyle("-fx-fill: #F8F8F2;");
            algoritmo[i].setFont(new Font(14));
            algoritmo[i].setLayoutX(40 * TL + 150);
            algoritmo[i].setLayoutY(y);
            pane.getChildren().add(algoritmo[i]);
        } else {
            Text texto = new Text(linha);
            texto.setStyle("-fx-fill: #F8F8F2;");
            texto.setFont(new Font(14));
            texto.setLayoutX(40 * TL + 150);
            texto.setLayoutY(y);
            pane.getChildren().add(texto);
        }
    }

    private void gerarAlgoritmo() {
        algoritmo = new Text[16];
        int i = 0, j = 100;
        adicionarLinha("public void heapSort() {", j += 25, -1);
        adicionarLinha("|\tint TL2 = TL, aux, fe, fd, maior;", j += 25, i++);
        adicionarLinha("|\twhile (TL2 > 1) {", j += 25, i++);
        adicionarLinha("|\t|\tfor (int pai = TL2 / 2 - 1; pai >= 0; pai--) {", j += 25, i++);
        adicionarLinha("|\t|\t|\tfe = 2 * pai + 1;", j += 25, i++);
        adicionarLinha("|\t|\t|\tfd = fe + 1;", j += 25, i++);
        adicionarLinha("|\t|\t|\tmaior = fe;", j += 25, i++);
        adicionarLinha("|\t|\t|\tif (fd < TL2 && vet[fd] > vet[fe])", j += 25, i++);
        adicionarLinha("|\t|\t|\t|\tmaior = fd;", j += 25, i++);
        adicionarLinha("|\t|\t|\tif (vet[maior] > vet[pai]) {", j += 25, i++);
        adicionarLinha("|\t|\t|\t|\taux = vet[maior];", j += 25, i++);
        adicionarLinha("|\t|\t|\t|\tvet[maior] = vet[pai];", j += 25, i++);
        adicionarLinha("|\t|\t|\t|\tvet[pai] = aux;", j += 25, i++);
        adicionarLinha("|\t|\t|\t}", j += 25, -1);
        adicionarLinha("|\t|\t}", j += 25, -1);
        adicionarLinha("|\t|\taux = vet[0];", j += 25, i++);
        adicionarLinha("|\t|\tvet[0] = vet[TL2 - 1];", j += 25, i++);
        adicionarLinha("|\t|\tvet[TL2 - 1] = aux;", j += 25, i++);
        adicionarLinha("|\t|\tTL2--;", j += 25, i++);
        adicionarLinha("|\t}", j += 25, -1);
        adicionarLinha("}", j += 25, -1);
    }

    private void iniciarCena(Stage stage) {
        TL = 14;
        int tam = 40 * TL + 750;
        // Apenas para demonstração de diferentes tamanhos de vetor.
        if (tam < 760)
            tam = 760;
        // --------------------------
        stage.setTitle("Pesquisa e Ordenação - Heap Sort");
        pane = new AnchorPane();
        pane.setStyle("-fx-background-color: #282A36");
        gerarBotoes();
        gerarAlgoritmo();
        gerarVetor();
        gerarArvore();
        Scene scene = new Scene(pane, tam, arv[TL - 1].getLayoutY() + 150);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage stage) {
        iniciarCena(stage);
    }

    public void iniciar() {
        Task<Void> task = new Task<Void>() {
            private int nivelB;
            private int nivelA;
            private double posA, posB;
            private final int ms = 300;

            @Override
            protected Void call() {
                heapSort();
                return null;
            }

            private void heapSort() {
                int TL2 = TL, fe, fd, maior;
                Platform.runLater(() -> algoritmo[0].setStyle("-fx-fill: #FF5555"));
                meuSleep();

                while (TL2 > 1) {
                    grifarLinha(1);
                    meuSleep();

                    for (int pai = TL2 / 2 - 1; pai >= 0; pai--) {
                        destacarPai(pai);
                        grifarLinha(2);
                        meuSleep();

                        fe = 2 * pai + 1;
                        destacarFe(fe);
                        grifarLinha(3);
                        meuSleep();

                        grifarLinha(4);
                        meuSleep();
                        fd = fe + 1;

                        if (fd < TL2) {
                            destacarFd(fd);
                            meuSleep();
                        }
                        maior = fe;
                        destacarMaior(maior);
                        grifarLinha(5);
                        meuSleep();

                        grifarLinha(6);
                        meuSleep();
                        if (fd < TL2 && parseInt(vet[fe].getText()) < parseInt(vet[fd].getText())) {
                            maior = fd;
                            destacarFe(fe);
                            destacarMaior(maior);
                            grifarLinha(7);
                            meuSleep();
                        } else
                            limparLinha(6);
                        grifarLinha(8);
                        meuSleep();
                        if (parseInt(vet[maior].getText()) > parseInt(vet[pai].getText())) {
                            grifarPermutacao(9);
                            meuSleep();
                            trocarVetor(maior, pai);
                            limparPermutacao(9);
                        } else
                            limparLinha(8);
                        limparPai(pai);
                        limparFe(fe);
                        limparFd(fd, TL2);
                        limparMaior(maior);
                    }
                    grifarPermutacao(12);
                    meuSleep();
                    trocarVetor(TL2 - 1, 0);
                    limparPermutacao(12);
                    ordenado(TL2 - 1);
                    grifarLinha(15);
                    TL2--;
                    diminuirTL2(TL2);
                    meuSleep();
                    limparLinha(15);
                }
                ordenado(0);
                meuSleep();
            }

            private void ordenado(int no) {
                Platform.runLater(() -> {
                    estilizarBotao(vet[no], "#8BE9FD", "#282A36", "#FFB86C");
                    estilizarBotao(arv[no], "#FFB86C", "#282A36", "#50fA7B");
                });
            }

            private void diminuirTL2(int TL2) {
                Platform.runLater(() -> {
                    botaoTL2.setText("TL2: " + TL2);
                });
            }

            private void destacarMaior(int maior) {
                Platform.runLater(() -> {
                    estilizarBotao(arv[maior], "#50fA7B", "#282A36", "#44475A");
                    botaoMaior.setText("Maior: " + vet[maior].getText());
                    indice[maior].setStyle("-fx-fill: #50fA7B");
                });
            }

            private void limparMaior(int maior) {
                Platform.runLater(() -> {
                    indice[maior].setStyle("-fx-fill: #F8F8F2");
                    estilizarBotao(arv[maior], "#FFB86C", "#282A36", "#44475A");
                });
            }

            private void destacarFd(int fd) {
                Platform.runLater(() -> {
                    estilizarBotao(arv[fd], "#FF79C6", "#282A36", "#44475A");
                    botaoFD.setText("FD: " + vet[fd].getText());
                    indice[fd].setStyle("-fx-fill: #FF79C6");
                });
            }

            private void limparFd(int fd, int tl2) {
                if (fd < tl2) {
                    indice[fd].setStyle("-fx-fill: #F8F8F2");
                    estilizarBotao(arv[fd], "#FFB86C", "#282A36", "#44475A");

                }
            }

            private void destacarFe(int fe) {
                Platform.runLater(() -> {
                    estilizarBotao(arv[fe], "#BD93F9", "#282A36", "#44475A");
                    botaoFE.setText("FE: " + vet[fe].getText());
                    indice[fe].setStyle("-fx-fill: #BD93F9");
                });
            }

            private void limparFe(int fe) {
                Platform.runLater(() -> {
                    estilizarBotao(arv[fe], "#FFB86C", "#282A36", "#44475A");
                    indice[fe].setStyle("-fx-fill: #F8F8F2");
                });
            }

            private void destacarPai(int pai) {
                Platform.runLater(() -> {
                    estilizarBotao(arv[pai], "#FF5555", "#F8F8F2", "#44475A");
                    botaoPai.setText("Pai: " + vet[pai].getText());
                    indice[pai].setStyle("-fx-fill: #FF5555");
                });
            }

            private void limparPai(int pai) {
                Platform.runLater(() -> {
                    estilizarBotao(arv[pai], "#FFB86C", "#282A36", "#44475A");
                    indice[pai].setStyle("-fx-fill: #F8F8F2");
                });
            }

            private void meuSleep() {
                try {
                    sleep(ms);
                } catch (
                        InterruptedException e) {
                    e.printStackTrace();
                }
            }

            private void limparLinha(int linha) {
                Platform.runLater(() -> algoritmo[linha].setStyle("-fx-fill: #F8F8F2"));
            }

            private void grifarLinha(int linha) {
                Platform.runLater(() -> {
                    algoritmo[linha - 1].setStyle("-fx-fill: #F8F8F2");
                    algoritmo[linha].setStyle("-fx-fill: #FF5555");
                });
            }

            private void limparPermutacao(int linhaI) {
                algoritmo[linhaI].setStyle("-fx-fill: #F8F8F2");
                algoritmo[linhaI + 1].setStyle("-fx-fill: #F8F8F2");
                algoritmo[linhaI + 2].setStyle("-fx-fill: #F8F8F2");
            }

            private void grifarPermutacao(int linhaI) {
                Platform.runLater(() -> {
                    algoritmo[linhaI - 1].setStyle("-fx-fill: #F8F8F2");
                    algoritmo[linhaI].setStyle("-fx-fill: #FF5555");
                    algoritmo[linhaI + 1].setStyle("-fx-fill: #FF5555");
                    algoritmo[linhaI + 2].setStyle("-fx-fill: #FF5555");
                });
            }

            private int buscaNivel(int pos) {
                int nivel = 0, atual = pos;
                if (pos > 0 && pos < TL) { // Calcula o índice do pai
                    while (atual > 0) {
                        nivel++;
                        atual = (atual - 1) / 2;
                    }
                    return nivel + 1;
                }
                return 1;
            }

            private void trocarVetor(int a, int b) {
                Platform.runLater(() -> {
                    estilizarBotao(arv[a], "#F1FA8C", "#282A36", "#44475A");
                    estilizarBotao(arv[b], "#F1FA8C", "#282A36", "#44475A");
                    estilizarBotao(vet[a], "#F1FA8C", "#282A36", "#44475A");
                    estilizarBotao(vet[b], "#F1FA8C", "#282A36", "#44475A");
                });
                nivelA = buscaNivel(a);
                nivelB = buscaNivel(b);
                for (int i = 0; i < 10; i++) {
                    Platform.runLater(() -> arv[a].setLayoutY(arv[a].getLayoutY() + 8 * (nivelB - nivelA)));
                    Platform.runLater(() -> arv[b].setLayoutY(arv[b].getLayoutY() - 8 * (nivelB - nivelA)));
                    Platform.runLater(() -> vet[a].setLayoutY(vet[a].getLayoutY() + 5));
                    Platform.runLater(() -> vet[b].setLayoutY(vet[b].getLayoutY() - 5));
                    try {
                        sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                for (int i = 0; i < 16; i++) {
                    Platform.runLater(() -> vet[a].setLayoutX(vet[a].getLayoutX() + (b - a) * 2.5));
                    Platform.runLater(() -> vet[b].setLayoutX(vet[b].getLayoutX() - (b - a) * 2.5));
                    try {
                        sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                posA = arv[a].getLayoutX();
                posB = arv[b].getLayoutX();
                if (arv[a].getLayoutX() < arv[b].getLayoutX())
                    for (int i = 0; i < 10; i++) {
                        Platform.runLater(() -> arv[a].setLayoutX(arv[a].getLayoutX() + (posB - posA) / 10));
                        Platform.runLater(() -> arv[b].setLayoutX(arv[b].getLayoutX() - (posB - posA) / 10));
                        Platform.runLater(() -> vet[a].setLayoutY(vet[a].getLayoutY() - 5));
                        Platform.runLater(() -> vet[b].setLayoutY(vet[b].getLayoutY() + 5));
                        try {
                            sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                else
                    for (int i = 0; i < 10; i++) {
                        Platform.runLater(() -> arv[a].setLayoutX(arv[a].getLayoutX() + (posB - posA) / 10));
                        Platform.runLater(() -> arv[b].setLayoutX(arv[b].getLayoutX() - (posB - posA) / 10));
                        Platform.runLater(() -> vet[a].setLayoutY(vet[a].getLayoutY() - 5));
                        Platform.runLater(() -> vet[b].setLayoutY(vet[b].getLayoutY() + 5));
                        try {
                            sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                // Permutação na memória do vetor
                Button aux = vet[a];
                vet[a] = vet[b];
                vet[b] = aux;
                // Permutação na memória da árvore
                aux = arv[a];
                arv[a] = arv[b];
                arv[b] = aux;

                Platform.runLater(() -> {
                    estilizarBotao(arv[a], "#FFB86C", "#282A36", "#44475A");
                    estilizarBotao(arv[b], "#FFB86C", "#282A36", "#44475A");
                    estilizarBotao(vet[a], "#8BE9FD", "#282A36", "#44475A");
                    estilizarBotao(vet[b], "#8BE9FD", "#282A36", "#44475A");
                });

                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread = new Thread(task);
        thread.start();
    }
}