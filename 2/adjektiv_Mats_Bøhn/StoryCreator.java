import java.util.*;

public class StoryCreator
{
    private InputReader reader;
    private OutputWriter writer;
    private Random random;
    private Scanner scanner;
    private ArrayList<String>adjectives;
    private ArrayList<String>story;
    
    public StoryCreator()
    {
        reader = new InputReader();
        writer = new OutputWriter();
        random = new Random();
    }
    
    /**
     * Returns a random index value from an ArrayList of adjectives.
     */
    private String randomizer(String filename)
    {
        ArrayList<String> adjectives = reader.getWordsInFile(filename);
        int index = random.nextInt(adjectives.size());
        return adjectives.get(index);
    }
    
    /**
     * Creates a new adjective story where the user selects a story file and an
     * adjective file as input sources and a name for an output file.
     */
    public void createAdjectiveStory(String storyFilename, 
    String adjectivesFilename, String outputFilename)
    {
        ArrayList<String> story = reader.getWordsInFile(storyFilename);
        for(int i = 0; i < story.size(); i++)
        {
            String word = story.get(i); 
            if(word.contains("ADJEKTIV."))
            {
                word = randomizer(adjectivesFilename) + (".");
                story.set(i, word);
            }
            if(word.contains("ADJEKTIV"))
            {
                word = randomizer(adjectivesFilename);
                story.set(i, word);
            } 
        }        
        writer.write(story, outputFilename);
        System.out.println(story);
    }

    /**
     * Creates a new adjective story where the user selects a story file, a name for
     * an output file and inputs adjectives in the terminal. Counts  
     * Typing "exit" prints the unfinished story.
     */
    public void createAdjectiveStory(String storyFilename, String outputFilename)
    {
        scanner = new Scanner(System.in);
        ArrayList<String> story = reader.getWordsInFile(storyFilename);
        System.out.println("Tast inn et adjektiv");
        int adjectiveNumber = 1;
        int count = -1;
        for(int i = 0; i < story.size(); i++)
        {   
            if(story.get(i).contains("ADJEKTIV."))
            {
                count++;
            }
            if(story.get(i).contains("ADJEKTIV"))
            {
                count++;
            }
        }        
        for(int i = 0; i < story.size(); i++)
        {       
            if(story.get(i).contains("ADJEKTIV."))
            {
                System.out.print("Adjektiv nummer " + adjectiveNumber + "/" 
                + count + ": ");
                String word = scanner.nextLine() + (".");
                story.set(i, word); 
                adjectiveNumber++;
                if(word.equals("exit"))
                { 
                    break;
                }
            }
            if(story.get(i).contains("ADJEKTIV"))
            {
                System.out.print("Adjektiv nummer " + adjectiveNumber + "/" 
                + count + ": ");
                String word = scanner.nextLine();
                story.set(i, word); 
                adjectiveNumber++;
                if(word.equals("exit"))
                { 
                    break;
                }
            }
        }
        System.out.println(story);
        writer.write(story, outputFilename);
    }
    
    /**
     * Creates a new adjective story where the user selects a story file, a
     * dictionary file and a name for an output file.
     */
    public void createAdjectiveStoryFromDictionary(String storyFilename, 
    String dictionaryFilename, String outputFilename)
    {
        adjectives = getAdjectives(dictionaryFilename);
        story = reader.getWordsInFile(storyFilename);
        for(int i = 0; i < story.size(); i++)
        {
            int index = random.nextInt(adjectives.size());
            if(story.get(i).contains("ADJEKTIV."))
            {
                String word = adjectives.get(index) + (".");
                story.set(i, word);
            }
            if(story.get(i).contains("ADJEKTIV"))
            {
                String word = adjectives.get(index);
                story.set(i, word); 
            }
        }
        System.out.println(story);
        writer.write(story, outputFilename);
    }
    
    /**
     * Returns an adjective from an ArrayList of a dictionary files adjectives and converts them to
     * lower-case.
     */
    private ArrayList<String> getAdjectives(String dictionaryFilename){
        ArrayList<String> dictionary = reader.getWordsInFile(dictionaryFilename);
        ArrayList<String> adjective = new ArrayList<>();
        for(int i = 0; i < dictionary.size(); i++){
            if(dictionary.get(i).equals("adj")){
                adjective.add(dictionary.get(i - 1).toLowerCase());
            }
        }
        return adjective;
    }
}
