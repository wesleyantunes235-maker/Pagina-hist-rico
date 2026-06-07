import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Historico extends JFrame {
    static class Contato {
        String nome;
        String tipo;
        boolean favorito;

        Contato(String nome, String tipo, boolean favorito) {
            this.nome = nome;
            this.tipo = tipo;
            this.favorito = favorito;
        }
    }

    ArrayList<Contato> historicoEmail = new ArrayList<>();
    ArrayList<Contato> historicoWhatsapp = new ArrayList<>();
    ArrayList<Contato> historicoLigar = new ArrayList<>();

    JPanel painelHistoricoEmail;
    JPanel painelHistoricoWhatsapp;
    JPanel painelHistoricoLigar;

    JToggleButton toggleFavoritosEmail;
    JToggleButton toggleFavoritosWhatsapp;
    JToggleButton toggleFavoritosLigar;

    JTextField campoPesquisaEmail;
    JTextField campoPesquisaWhatsapp;
    JTextField campoPesquisaLigar;

    public Historico() {
        setTitle("App Contatos");
        setSize(400, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);
        Dados();

        JTabbedPane abas = new JTabbedPane();
        abas.setBackground(Color.BLACK);
        abas.setForeground(Color.WHITE);

        JPanel abaWhatsapp = criarAba("whatsapp", historicoWhatsapp);
        JPanel abaEmail = criarAba("email", historicoEmail);
        JPanel abaLigar = criarAba("ligar", historicoLigar);

        abas.addTab("whatsapp", abaWhatsapp);
        abas.addTab("email", abaEmail);
        abas.addTab("ligar", abaLigar);

        add(abas);
    }

    private void Dados() {
        historicoEmail.add(new Contato("Ana Silva", "enviado", true));
        historicoEmail.add(new Contato("Carlos Souza", "recebido", false));
        historicoEmail.add(new Contato("Ana Silva", "enviado", true));   // 2º email para Ana
        historicoEmail.add(new Contato("Bruno Lima", "recebido", true));
        historicoEmail.add(new Contato("Carlos Souza", "enviado", false));
        historicoEmail.add(new Contato("Daniela Rocha", "recebido", false));
        historicoEmail.add(new Contato("Ana Silva", "recebido", true));

        historicoWhatsapp.add(new Contato("Fernando Alves", "enviado", false));
        historicoWhatsapp.add(new Contato("Gabriela Costa", "recebido", true));
        historicoWhatsapp.add(new Contato("Fernando Alves", "recebido", false));
        historicoWhatsapp.add(new Contato("Helena Martins", "enviado", true));

        historicoLigar.add(new Contato("Igor Pereira", "enviado", true));
        historicoLigar.add(new Contato("Julia Santos", "recebido", false));
        historicoLigar.add(new Contato("Igor Pereira", "recebido", true));
    }

    private JPanel criarAba(String nomeAba, ArrayList<Contato> dados) {
        JPanel aba = new JPanel();
        aba.setLayout(new BorderLayout());
        aba.setBackground(Color.BLACK);

        JPanel painelTopo = new JPanel();
        painelTopo.setLayout(new BoxLayout(painelTopo, BoxLayout.Y_AXIS));
        painelTopo.setBackground(Color.BLACK);
        painelTopo.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel lblFavoritos = new JLabel("favoritos");
        lblFavoritos.setForeground(Color.WHITE);
        lblFavoritos.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelTopo.add(lblFavoritos);
        painelTopo.add(Box.createVerticalStrut(5));

        JToggleButton toggleFav = new JToggleButton("off");
        toggleFav.setPreferredSize(new Dimension(150, 35));
        toggleFav.setMaximumSize(new Dimension(150, 35));
        toggleFav.setAlignmentX(Component.CENTER_ALIGNMENT);
        toggleFav.setBackground(new Color(50, 50, 50));
        toggleFav.setForeground(Color.WHITE);
        toggleFav.setFocusPainted(false);
        toggleFav.setBorder(new EmptyBorder(5, 15, 5, 15));

        toggleFav.addActionListener(e -> {
            if (toggleFav.isSelected()) {
                toggleFav.setText("on");
                toggleFav.setBackground(new Color(0, 150, 0));
            } else {
                toggleFav.setText("off");
                toggleFav.setBackground(new Color(50, 50, 50));
            }
            atualizarHistorico(nomeAba, dados, toggleFav, getCampoPesquisa(nomeAba));
        });

        painelTopo.add(toggleFav);
        painelTopo.add(Box.createVerticalStrut(10));

        // JTextField pesquisar
        JTextField campoPesquisa = new JTextField("pesquisar");
        campoPesquisa.setPreferredSize(new Dimension(250, 35));
        campoPesquisa.setMaximumSize(new Dimension(250, 35));
        campoPesquisa.setAlignmentX(Component.CENTER_ALIGNMENT);
        campoPesquisa.setBackground(new Color(50, 50, 50));
        campoPesquisa.setForeground(Color.WHITE);
        campoPesquisa.setHorizontalAlignment(JTextField.CENTER);
        campoPesquisa.setBorder(new EmptyBorder(5, 10, 5, 10));

        campoPesquisa.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                atualizarHistorico(nomeAba, dados, toggleFav, campoPesquisa);
            }
        });

        painelTopo.add(campoPesquisa);

        // Salvar referências
        if (nomeAba.equals("email")) {
            toggleFavoritosEmail = toggleFav;
            campoPesquisaEmail = campoPesquisa;
        } else if (nomeAba.equals("whatsapp")) {
            toggleFavoritosWhatsapp = toggleFav;
            campoPesquisaWhatsapp = campoPesquisa;
        } else {
            toggleFavoritosLigar = toggleFav;
            campoPesquisaLigar = campoPesquisa;
        }

        aba.add(painelTopo, BorderLayout.NORTH);

        // Painel do histórico (JPanel com botões)
        JPanel painelHistorico = new JPanel();
        painelHistorico.setLayout(new BoxLayout(painelHistorico, BoxLayout.Y_AXIS));
        painelHistorico.setBackground(Color.BLACK);
        painelHistorico.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        if (nomeAba.equals("email")) {
            painelHistoricoEmail = painelHistorico;
        } else if (nomeAba.equals("whatsapp")) {
            painelHistoricoWhatsapp = painelHistorico;
        } else {
            painelHistoricoLigar = painelHistorico;
        }

        JScrollPane scroll = new JScrollPane(painelHistorico);
        scroll.setBackground(Color.BLACK);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        aba.add(scroll, BorderLayout.CENTER);

        // Painel inferior (botões de ação)
        JPanel painelInferior = new JPanel();
        painelInferior.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelInferior.setBackground(Color.BLACK);
        painelInferior.setBorder(new CompoundBorder(
                new LineBorder(Color.WHITE, 2, true),
                new EmptyBorder(5, 10, 5, 10)
        ));

        String textoNovo = nomeAba.equals("email") ? "novo email" : "novo cont";

        JButton btnNovo = criarBotaoAcao(textoNovo);
        JButton btnExportar = criarBotaoAcao("exportar");
        JButton btnSair = criarBotaoAcao("sair");

        // Sair - fecha o aplicativo
        btnSair.addActionListener(e -> System.exit(0));

        painelInferior.add(btnNovo);
        painelInferior.add(btnExportar);
        painelInferior.add(btnSair);

        aba.add(painelInferior, BorderLayout.SOUTH);

        // Renderizar histórico inicial
        atualizarHistorico(nomeAba, dados, toggleFav, campoPesquisa);

        return aba;
    }

    private void atualizarHistorico(String nomeAba, ArrayList<Contato> dados,
                                    JToggleButton toggleFav, JTextField campoPesquisa) {

        JPanel painelHistorico = getPainelHistorico(nomeAba);
        painelHistorico.removeAll();

        boolean filtrarFavoritos = toggleFav.isSelected();
        String termoPesquisa = campoPesquisa.getText().trim().toLowerCase();

        for (Contato c : dados) {
            // Filtro de favoritos
            if (filtrarFavoritos && !c.favorito) {
                continue;
            }

            // Filtro de pesquisa
            if (!termoPesquisa.isEmpty() && !termoPesquisa.equals("pesquisar")) {
                if (!c.nome.toLowerCase().contains(termoPesquisa)) {
                    continue;
                }
            }

            // Criar botão do histórico
            JButton btnHistorico = new JButton(c.nome);
            btnHistorico.setPreferredSize(new Dimension(320, 45));
            btnHistorico.setMaximumSize(new Dimension(320, 45));
            btnHistorico.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnHistorico.setFocusPainted(false);
            btnHistorico.setBorder(new EmptyBorder(5, 15, 5, 15));
            btnHistorico.setForeground(Color.WHITE);
            btnHistorico.setFont(new Font("SansSerif", Font.PLAIN, 14));

            // Cor baseada no tipo (apenas para aba email)
            if (nomeAba.equals("email")) {
                if (c.tipo.equals("enviado")) {
                    btnHistorico.setBackground(new Color(30, 80, 180)); // AZUL
                } else {
                    btnHistorico.setBackground(new Color(180, 160, 30)); // AMARELO
                }
            } else {
                btnHistorico.setBackground(new Color(50, 50, 50)); // cinza padrão
            }

            // Tooltip com info
            btnHistorico.setToolTipText(c.nome + " - " + c.tipo +
                    (c.favorito ? "+" : ""));

            painelHistorico.add(btnHistorico);
            painelHistorico.add(Box.createVerticalStrut(5));
        }

        painelHistorico.revalidate();
        painelHistorico.repaint();
    }

    private JButton criarBotaoAcao(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(new Color(50, 50, 50));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 20, 8, 20));
        btn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        return btn;
    }

    private JPanel getPainelHistorico(String nomeAba) {
        if (nomeAba.equals("email")) return painelHistoricoEmail;
        if (nomeAba.equals("whatsapp")) return painelHistoricoWhatsapp;
        return painelHistoricoLigar;
    }

    private JTextField getCampoPesquisa(String nomeAba) {
        if (nomeAba.equals("email")) return campoPesquisaEmail;
        if (nomeAba.equals("whatsapp")) return campoPesquisaWhatsapp;
        return campoPesquisaLigar;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Historico app = new Historico();
            app.setVisible(true);
        });
    }
}