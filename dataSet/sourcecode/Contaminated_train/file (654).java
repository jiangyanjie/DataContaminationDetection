package appelgebakje22.Sneek.Library.ServerLibrary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import appelgebakje22.Sneek.Library.SFrame;
import appelgebakje22.Sneek.Library.STextArea;

public class ConsoleFrame extends SFrame implements Runnable {

	private static final long	serialVersionUID	= -3114106865094563957L;
	private final ISneekServer	Server;
	private final STextArea		ConsoleArea			= new STextArea();
	private final JPanel		CommandPanel		= new JPanel();
	private final JTextField	CommandField		= new JTextField();
	private final JButton		ButtonClose			= new JButton("Exit");

	public ConsoleFrame(ISneekServer Server)
	{
		super("Sneek Server (Console)", 500, 350);
		this.Server = Server;
		this.setDefaultCloseOperation(SFrame.DO_NOTHING_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(this.ConsoleArea), BorderLayout.CENTER);
		this.CommandField.addActionListener(this);
		this.CommandPanel.setLayout(new BorderLayout());
		this.CommandPanel.add(this.CommandField, BorderLayout.CENTER);
		this.ButtonClose.addActionListener(this);
		this.ButtonClose.setText("Exit");
		this.CommandPanel.add(this.ButtonClose, BorderLayout.EAST);
		this.add(this.CommandPanel, BorderLayout.PAGE_END);
	}

	@Override
	public void run()
	{
		this.setVisible(true);
		new Thread(new UpdateFrame(this)).start();
	}

	public void AddLine(String Data)
	{
		this.AddLine(Data, Color.black);
	}

	public void AddLine(String Data, Color TextColor)
	{
		this.ConsoleArea.AddLine(Data, TextColor);
		System.out.println(Data);
	}

	@Override
	public void PerformAction(ActionEvent Event)
	{
		if (Event.getSource() == this.ButtonClose)
			this.Server.ProcessCommand(SneekCommand.EXIT.GetCommand());
		else
		{
			Class<? extends ISneekCommand> Command = SneekCommand.GetCommand(this.CommandField.getText());
			if (Command == null) this.AddLine("Invalid Command", Color.red);
			if (Command == SneekCommand.HELP.GetCommand())
				this.PrintCommands();
			else
				this.Server.ProcessCommand(Command);
		}
		this.CommandField.setText(null);
	}

	private void PrintCommands()
	{
		for (SneekCommand Command : SneekCommand.values())
			this.AddLine(Command.toString(), Color.GREEN.darker().darker());
	}

	@Override
	public void setVisible(boolean Visible)
	{
		super.setVisible(Visible);
		this.AddLine("Type 'exit' or 'stop' to close", Color.GREEN.darker().darker());
		this.AddLine("Type 'help' to show all commands", Color.GREEN.darker().darker());
	}

	private class UpdateFrame implements Runnable {
		private final ConsoleFrame	Console;

		private UpdateFrame(ConsoleFrame Console)
		{
			this.Console = Console;
		}

		@Override
		public void run()
		{
			while (this.Console.isVisible())
			{
				try
				{
					this.Console.ConsoleArea.setCaretPosition(this.Console.ConsoleArea.getDocument().getLength());
				}
				catch (IllegalArgumentException e)
				{
					this.Console.ConsoleArea.SetText("");
					this.Console.ConsoleArea.setCaretPosition(0);
				}
			}
		}
	}
}