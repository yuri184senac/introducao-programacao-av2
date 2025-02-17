import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Aluno;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 // Example date
		Scanner scanner = new Scanner(System.in);
		boolean cutout = true;
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("escola");
	    EntityManager manager = factory.createEntityManager();
	    
		do {			
			System.out.println("======= Menu =======");
	        System.out.println("1. Cadastrar");
	        System.out.println("2. Buscar");
	        System.out.println("3. Atualizar");
	        System.out.println("4. Deletar");
	        System.out.println("0. Sair");
	        System.out.println("====================");
	        char menu =  scanner.next().charAt(0);
	        	        
	        switch(menu) {	     
			
				//CREATE
	        	case '1':	        		   
	                 System.out.print("Digite o nome do aluno:");
	                 String nome = scanner.next().toLowerCase();
	                 
	                 System.out.print("Digite o email do aluno:");
	                 String email = scanner.next().toLowerCase();
	
	                 System.out.print("Digite o CPF do aluno:");
	                 String cpf = scanner.next();
	
	                 System.out.print("Digite a data de nascimento do aluno (no formato yyyy-MM-dd):");
	                 LocalDate dataNascimento = LocalDate.parse(scanner.next());
	
	                 System.out.print("Digite a naturalidade do aluno:");
	                 String naturalidade = scanner.next().toLowerCase();
	
	                 System.out.print("Digite o endereço do aluno:");
	                 String endereco = scanner.next().toLowerCase();
		                 	        	
	
	                 Aluno aluno = new Aluno(nome, email, cpf, dataNascimento, naturalidade, endereco);		                	            
	        		 manager.getTransaction().begin();
	       	      	 manager.persist(aluno);
	       	      	 manager.getTransaction().commit();
	       	      	 System.out.println("Cadastro realizado, ID: " + aluno.getId());
	        		break;
	        		
	        	//READ
	        	case '2':
        			String sql = "SELECT p FROM Aluno p WHERE p.nome LIKE :letraInicial";	        	     
        		 	System.out.print("Insira o nome do aluno: ");
        		 	String inicial = scanner.next().toLowerCase();
        	        Query query = manager.createQuery(sql);
        	        query.setParameter("letraInicial", inicial + "%");        	        	
        	        List<Aluno> listaAluno = query.getResultList();
        	        if (listaAluno.size() > 0) {
        	        	for (Aluno aluno1 : listaAluno){
        	        		aluno1.exibirDadosAluno(aluno1);;
        	        		System.out.println("");
        	        		System.out.println("==========================");
        	        		System.out.println("");
            	        }	
        	        } else {
        	        	System.out.println("Aluno não encontrado na base de dados");
        	        }
    	        	
	        		break;
	        		
	        	//UPDATE
	        	case '3':
	        			manager.getTransaction().begin();
	        			System.out.print("Insira o ID do Aluno: ");
	        			long id =  scanner.nextInt();	        				        		 
	        	        Aluno alunoUpdate = manager.find(Aluno.class, id);
	                if (alunoUpdate != null) {
	                	System.out.print("Novo endereço do aluno: ");
	                    String novoEndereco = scanner.next().toString();
	                    alunoUpdate.setEndereco(novoEndereco);;

	                    manager.merge(alunoUpdate);
	                    manager.getTransaction().commit();
	                    
	                    System.out.println("Aluno atualizado com sucesso: " + alunoUpdate.getId());
	                } else {
	                    System.out.println("Aluno não encontrado.");
	                }
	                	               
	        		break;
	        		
	        	//DELETE	        		
	        	case '4':
	                Aluno alunoDel =  new Aluno();
	                System.out.print("Aluno ID: ");
	                Long id1 = scanner.nextLong();
	                alunoDel.setId(id1);
	                aluno = manager.find(Aluno.class, alunoDel.getId());

	                manager.getTransaction().begin();
	                manager.remove(aluno);
	                manager.getTransaction().commit();
	                System.out.println("Aluno removido com sucesso!");
	                break;
	        		
	        	//EXIT
	        	case '0':
	        		System.out.println("O programa foi encerrado com sucesso !");
	        		cutout = false;
	        		factory.close();
	        		manager.close();
	        		break;	
	        	default :
	        		System.out.println("Opção inválida");
			}	      	       
	        manager.clear();	       	    		        
		} while(cutout);
		 System.out.println("O programa foi encerrado com sucesso !");
		 scanner.close();
		
	}

}