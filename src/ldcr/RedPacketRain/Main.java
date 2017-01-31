package ldcr.RedPacketRain;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main 
extends JavaPlugin
{
	static Plugin plugin ;
	public void onEnable()
	  {
		  PluginManager pm = Bukkit.getServer().getPluginManager();
		  pm.registerEvents(new EventListener(), this);
		  plugin=this;
		  if (pm.getPlugin("Vault")!=null)
		  {
			  VaultUtil.loadeco();
			  getLogger().info("�ҵ�Vault! ʹ��"+VaultUtil.eco.getName()+"��Ǯ���!"); 
			  
			  getLogger().info("�������������ļ�...");
			  if(!getDataFolder().exists())
			  {
				  getLogger().info("���Ŀ¼������,���ڴ���....");
				  getDataFolder().mkdir();
			  }
			  File cfile=new File(getDataFolder(),"config.yml");
			  if (!(cfile.exists()))
			  {
				  getLogger().info("�����ļ�������,���ڴ���....");
				  saveDefaultConfig();
			  }
			  reloadConfig();
			  Config.config = YamlConfiguration.loadConfiguration(cfile);
			  
			  Config.Multiply=Config.config.getInt("Multiply");
			  Config.MinTotal=Config.config.getDouble("MinTotal");
			  Config.MinPerPacket=Config.config.getDouble("MinPerPacket");
			  Config.MaxRedPacketCount=Config.config.getDouble("MaxRedPacketCount");
			  Config.Cost=Config.config.getDouble("Cost");
			  
			  getLogger().info("RedPacketRain������� �Ѽ���  By.Ldcr~");  
		  }
		  else
		  {
			  getLogger().log(Level.WARNING,"δ�ҵ�Vault,����Զ�����!"); 
			  pm.disablePlugin(this);
		  }
	  }
	  public void onDisable()
	  {
		  getLogger().info("RedPacketRain������� ��ж��  By.Ldcr~");
	  }
	  
	  public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args)
	  {
	    if (lable.equalsIgnoreCase("redpacketrain") || lable.equalsIgnoreCase("rpr")|| lable.equalsIgnoreCase("redpacket"))
	    {
	    	if (!(sender instanceof Player))
	    	{
	    		Bukkit.getServer().getLogger().info("����̨�޷�ʹ�ô�ָ��~");
	    	}
	    	else if (args.length == 0)
	    	{
	    		//���Ͱ�����Ϣ
	    		String[] helpstrArray={"��c����������������������","��c������6����c������������6����c����","��c��������6����c��������6����c������  ��b��l/redpacketrain ��a��������:","��c����������6����c����6����c��������","��c������������6����c����c��������  ��e<��Ǯ��> ��5����ȥ��Ǯ������","��c������������6����c����������  ��e<�����> ��5�������","��c��������6������������c������  ��e<ף����> ��5��Ҽ񵽺����������ף���� ","��c������������6����c����������","��c������������6����c����������             ��b��lRedPacketRain �������","��c������a���c������6����c������a���c����                                  ��6��lBy.Ldcr","��c����������������������"};
	    		sender.sendMessage(helpstrArray);	
	    	}
	    	else if ( args[0].equalsIgnoreCase("make") && sender.hasPermission("redpacketrain.make"))
	    	{
	    		if (args.length ==3 )
	    		{
	    			Player p =(Player) sender;
	    			if (p.getInventory().getItemInMainHand()!=null)
	    			{
	    				Util.setStackDisplayName(p.getInventory().getItemInMainHand(), "��6RedPakcet&#||#&"+args[1]+"&#||#&"+args[2]);
	    			}
	    			else
	    			{
	    				sender.sendMessage("��6[�����] ��c�뽫һ����Ʒ������������Ϊ���!");
	    			}
	    		}
	    		else
	    		{
	    			sender.sendMessage("��6[�����] ��cʹ�÷���: /redpacketrain make ���Ǯ�� ף����");
	    		}
	    	}
	    	else if (sender.hasPermission("redpacketrain.use"))
	    	{
	    		// ���� ���͵�Ǯ��(���ܵ���1) ���ͺ������(���ܵ���1,����Ϊ����,���ܸ���Ǯ��,���ܸ���30) ף����
	    		if (args.length != 3)
	    		{
	    			//���Ͱ�����Ϣ
	    			String[] helpstrArray={"��c����������������������","��c������6����c������������6����c����","��c��������6����c��������6����c������  ��b��l/redpacketrain ��a��������:","��c����������6����c����6����c��������","��c������������6����c����c��������  ��e<��Ǯ��> ��5����ȥ��Ǯ������","��c������������6����c����������  ��e<�����> ��5�������","��c��������6������������c������  ��e<ף����> ��5��Ҽ񵽺����������ף���� ","��c������������6����c����������","��c������������6����c����������             ��b��lRedPacketRain �������","��c������a���c������6����c������a���c����                                  ��6��lBy.Ldcr","��c����������������������"};
	    			sender.sendMessage(helpstrArray);	
	    		}
	    		else
	    		{
	    			Double money = Double.parseDouble(args[0]);
	    			int count = (int) (Double.parseDouble(args[1]));
	    			if (args[2].equalsIgnoreCase("no"))
	    			{args[2]="&f";}
	    			if (money >=Config.MinTotal)
	    			{
	    				if (count>=1)
	    				{  
	    					if (count<=Config.MaxRedPacketCount)
	    					{
	    						if ((money/count)>=Config.MinPerPacket)
	    						{
	    							Player p =(Player) sender;
	    							if (VaultUtil.eco.has(p, money+Config.Cost))
	    							{
	    								VaultUtil.eco.withdrawPlayer(p.getPlayer(), money+Config.Cost);
	    								if (Config.Cost!=0)
	    								{
	    									sender.sendMessage("��6[�����] ��a��ɹ����� ��6"+count+" ��a�����,�۳� ��6"+money+" ��aԪ! ��c(����۳�������"+Config.Cost+"Ԫ)");
	    								}
	    								else
	    								{
	    									
	    									sender.sendMessage("��6[�����] ��a��ɹ����� ��6"+count+" ��a�����,�۳� ��6"+money+" ��aԪ!");
	    								}
	    								Util.shootFirework(p, money+"&#||#&"+count+"&#||#&"+args[2]);
	    								Util.boardcast("&6&l���� &a&l"+p.getName()+"&6&l ������(&c&l"+p.getWorld().getName()+" "+(int)p.getLocation().getX()+","+(int)p.getLocation().getY()+","+(int)p.getLocation().getZ()+"&6&l)����һ�� &b&l"+money+"&6&l Ԫ�����~");
	    							}
	    							else
	    							{
	    								sender.sendMessage("��6[�����] ��c��û����ô��Ǯ�������!");
	    							}
	    						}
	    						else
	    						{
	    							sender.sendMessage("��6[�����] ��cÿ������ڵĽ�Ǯ���ܵ���"+Config.MinPerPacket+"��,�볢������Ǯ����ٺ������!");
	    						}
	    					}	
	    					else
	    					{
	    						sender.sendMessage("��6[�����] ��c��������ܸ���"+Config.MaxRedPacketCount+"!");
	    					}
	    				}
	    				else
	    				{
	    					sender.sendMessage("��6[�����] ��c������Ҫ����һ�����!");
	    				}
	    			}
	    			else
	    			{
	    				sender.sendMessage("��6[�����] ��c��������Ҫ����������"+Config.MinTotal+"ԪǮ!");
	    			}	
	    		}
	    	}
	    	return true;
	    }
	    return false;
	  }	
}		
