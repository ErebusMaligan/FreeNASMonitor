package modules.log.ui.panel.log;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import modules.log.module.LogModule;
import modules.log.state.log.LogEntry;
import statics.GU;
import statics.UIUtils;
import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;


/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 3, 2015, 2:33:39 AM 
 */
public class WarningDialog extends JDialog implements Observer {

	private static final long serialVersionUID = 1L;

	private DefaultListModel<LogEntry> model = new DefaultListModel<>();
	
	private JList<LogEntry> list = new JList<>( model );
	
	public WarningDialog( ApplicationState state ) {
		super( state.getUIManager().getFrame(), "Warnings", false );
		this.setSize( 300, 300 );
		this.setDefaultCloseOperation( HIDE_ON_CLOSE );
		this.setLayout( new BorderLayout() );
		JScrollPane scroll = UIUtils.setJScrollPane( new JScrollPane( list ) );
		this.add( scroll, BorderLayout.CENTER );
		list.setSelectionMode( ListSelectionModel.SINGLE_INTERVAL_SELECTION );
		List<LogEntry> l = (state.getModuleManager().getModuleByModuleClass( LogModule.class ) ).getLogData().getLogsByType( LogEntry.Type.WARNING );
		for ( LogEntry e : l ) {
			model.addElement( e );
		}
		list.setCellRenderer( new WarningListCellRenderer() );
		JPanel p = new JPanel();
		JButton ack = GU.createButton( AC.ACK, e -> { 
			List<LogEntry> v = list.getSelectedValuesList();
			if ( !v.isEmpty() ) {
				for ( LogEntry le : v ) {
					le.ack();
					repaint();
				}
			}
		} );
		JButton del = GU.createButton( AC.DEL, e -> { 
			List<LogEntry> v = list.getSelectedValuesList();
			if ( !v.isEmpty() ) {
				for ( LogEntry le : v ) {
					model.removeElement( le );
					( state.getModuleManager().getModuleByModuleClass( LogModule.class ) ).getLogData().delete( le );
				}
			}
		} );
		UIUtils.setColors( GU.hp( p, ack, del ), p, del, ack );
		this.add( p, BorderLayout.SOUTH );
		( state.getModuleManager().getModuleByModuleClass( LogModule.class ) ).getLogData().addObserver( this );
		UIUtils.setColors( this, list, scroll.getViewport() );
	}
	
	@Override
	public void update( Observable o, Object v ) {
		model.addElement( (LogEntry)v );
	}

}